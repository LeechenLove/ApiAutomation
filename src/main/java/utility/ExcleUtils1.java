package utility;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/21 10:04
 **/
public class ExcleUtils1 {

    // 根据Excel文件路径，返回所有sheet数据
    /**
     * 获取excel表所有sheet数据
     * @param clz
     * @param path
     * @return
     */
    public static <T> List<T> readExcel(Class<T> clz, String path) {
        System.out.println(path);
        if (null == path || "".equals(path)) {
            return null;
        }
        InputStream inputStream;
        Workbook xssfWorkbook;
        try {
            inputStream = new FileInputStream(path);
            if (path.endsWith(".xls")) {
                xssfWorkbook = new HSSFWorkbook(inputStream);
            } else {
                xssfWorkbook = new XSSFWorkbook(inputStream);
            }
            inputStream.close();
            int sheetNumber = xssfWorkbook.getNumberOfSheets();
            List<T> allData = new ArrayList<T>();
            for (int i = 0; i < sheetNumber; i++) {
                allData.addAll(transToObject(clz, xssfWorkbook,
                        xssfWorkbook.getSheetName(i)));
            }
            return allData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换excel文件失败：" + e.getMessage());
        }
    }

    /**
     * 获取excel表指定sheet表数据
     * @param clz
     * @param path
     * @param sheetName
     * @return
     */
    public static <T> List<T> readExcel(Class<T> clz, String path,
                                        String sheetName) {
        if (null == path || "".equals(path)) {
            return null;
        }
        InputStream is;
        Workbook xssfWorkbook;
        try {
            is = new FileInputStream(path);
            if (path.endsWith(".xls")) {
                xssfWorkbook = new HSSFWorkbook(is);
            } else {
                xssfWorkbook = new XSSFWorkbook(is);
            }
            is.close();
            return transToObject(clz, xssfWorkbook, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换excel文件失败：" + e.getMessage());
        }

    }

    // 根据sheetname返回数据
    private static <T> List<T> transToObject(Class<T> clz, Workbook workbook, String sheetName) throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        Sheet xssfSheet = workbook.getSheet(sheetName);
        Row firstRow = xssfSheet.getRow(0);
        if (null == firstRow) {
            return list;
        }
        List<Object> heads = getRow(firstRow);
        heads.add("sheetName");
        Map<String, Method> headMethod = getSetMethod(clz, heads);
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            try {
                Row xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }
                T t = clz.newInstance();
                List<Object> data = getRow(xssfRow);
                //如果发现表数据的列数小于表头的列数，则自动填充为null，最后一位不动，用于添加sheetName数据
                while(data.size()+1 < heads.size()) {
                    data.add("");
                }
                data.add(sheetName);
                setValue(t, data, heads, headMethod);
                list.add(t);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // 通过反射的原理，调用实体类的set方法
    private static void setValue(Object obj, List<Object> data, List<Object> heads, Map<String, Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for(Map.Entry<String, Method> entry : methods.entrySet()) {
            Object value = "";
            int dataIndex = heads.indexOf(entry.getKey());
            if (dataIndex < data.size()) {
                value = data.get(heads.indexOf(entry.getKey()));
            }
            Method method = entry.getValue();
            Class<?> param = method.getParameterTypes()[0];
            if (String.class.equals(param)) {
                method.invoke(obj, value);
            } else if(Integer.class.equals(param) || int.class.equals(param)) {
                if(value.toString() == "") {
                    value = 0;
                }
                method.invoke(obj, new BigDecimal(value.toString()).intValue());
            } else if(Long.class.equals(param) || long.class.equals(param)) {
                if(value.toString() == "") {
                    value = 0;
                }
                method.invoke(obj, new BigDecimal(value.toString()).intValue());
            } else if (Short.class.equals(param) || short.class.equals(param)) {
                if(value.toString()==""){
                    value=0;
                }
                method.invoke(obj, new BigDecimal(value.toString()).shortValue());
            } else if (Boolean.class.equals(param)
                    || boolean.class.equals(param)) {
                method.invoke(obj, Boolean.valueOf(value.toString())
                        || value.toString().toLowerCase().equals("y"));
            } else if (JSONObject.class.equals(param)
                    || JSONObject.class.equals(param)) {
                method.invoke(obj, JSONObject.parseObject(value.toString()));
            }else {
                // Date
                method.invoke(obj, value);
            }
        }
    }

    // 循环表头，与实体类bean的set方法进行比较，如果相同，则放入map
    // 返回所有和Excel表头相同的set方法
    // 比如说表头有个字段run，对应bean里面有个方法setrun，将run：setrun存入map
    private static Map<String, Method> getSetMethod(Class<?> clz, List<Object> heads) {
        Map<String, Method> map = new HashMap<>();
        Method[] methods = clz.getMethods();
        for(Object head : heads) {
            for(Method method : methods) {
                if (method.getName().toLowerCase().equals("set" + head.toString().toLowerCase()) &&
                method.getParameterTypes().length == 1) {
                    map.put(head.toString(), method);
                    break;
                }
            }
        }
        return map;
    }

    // 根据行数返回当前行的数据
    private static List<Object> getRow(Row xssfRow) {
        List<Object> cells = new ArrayList<Object>();
        if(xssfRow != null) {
            for (short cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
                Cell xssfCell = xssfRow.getCell(cellNum);
                cells.add(getValue(xssfCell));
            }
        }
        return cells;
    }

    // 根据不同值的类型，返回String类型的数据
    private static String getValue(Cell cell) {
        if (null == cell) {
            return "";
        } else if(cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if(cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }
}
