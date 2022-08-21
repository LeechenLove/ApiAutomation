package utility;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;

/**
 * @Author: Lulu
 * @Description: 处理Excel数据的工具类
 * @DateTime: 2022/8/20 10:46
 **/
public class ExcelUtils {
    private static XSSFWorkbook ExcelWBook; // 表示一个Excel文件
    private static XSSFSheet ExcelWSheet;   // 表示Excel sheet
    private static XSSFRow Row;             // 行数据
    private static XSSFCell Cell;           // 单元格数据

    // 读取excel文件，根据sheetname参数获取对应sheet
    public static void setExcelFile(String path, String sheetName) throws Exception {
        try {
            FileInputStream excelfile = new FileInputStream(path);
            ExcelWBook = new XSSFWorkbook(excelfile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
        } catch (Exception e) {
            throw (e);
        }
    }

    // 根据行和列的位置，获取单元格数据
    public static String getCellData(int rownum, int colnum) {
        try {
            Cell = ExcelWSheet.getRow(rownum).getCell(colnum);
            CellType dataType = Cell.getCellType();
            if (dataType == CellType.BLANK) {
                return "";
            } else {
                String cellData = Cell.getStringCellValue();
                return cellData;
            }
        } catch (Exception e) {
            throw (e);
        }
    }

    // 将数据写入excel表格
    public static void setCellData(String result, int rownum, int colnum) {
        try {
            Row = ExcelWSheet.getRow(rownum);
            Cell = Row.getCell(colnum, RETURN_BLANK_AS_NULL);
            if (Cell == null) {
                Cell = Row.createCell(colnum);
                Cell .setCellValue(result);
            } else {
                Cell.setCellValue(result);
            }
            String excelPath = Paths.get(System.getProperty("user.dir"), "\\testcase\\testdata.xlsx").toString();
            FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
            ExcelWBook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取指定行的数据，返回二维数组
    public static Object[][] getTableArray(String path, String sheetName, int row) {
        String[][] tabArray = null;
        try {
            FileInputStream excelFile = new FileInputStream(path);
            ExcelWBook = new XSSFWorkbook(excelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
            int startCol = 1;
            int ci=0, cj = 0;
            int totalRows = 1;
            int totalCols = 2;
            tabArray = new String[totalRows][totalCols];
            for (int j = startCol; j <= totalCols; j++, cj++) {
                tabArray[ci][cj] = getCellData(row, j);
                System.out.println(tabArray[ci][cj]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabArray;
    }

    // 传入实例对象字符串，获取类名
    public static String getTestCaseName(String sTestCase) {
        String value = sTestCase;
        try {
            int posi = value.indexOf("@");
            value = value.substring(0, posi);
            posi = value.lastIndexOf(".");
            value = value.substring(posi + 1);
            return value;
        } catch (Exception e) {
            throw (e);
        }
    }

    // 根据testcase名称，返回当前行的数字
    public static int getRowContains(String sTestCaseName, int colNum) {
        int i;
        try {
            int rowCount = getRowUsed();
            for (i = 0; i < rowCount; i++) {
                if (getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
            return i;
        } catch (Exception e) {
            throw (e);
        }
    }

    public static int getRowUsed() {
        try {
            int rowCount = ExcelWSheet.getLastRowNum();
            return rowCount;
        } catch (Exception e) {
            throw (e);
        }
    }

    public static void main(String[] args) throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "\\testcase\\testdata.xlsx").toString();
        setExcelFile(excelPath, "Sheet1");
        String name = getCellData(1, 1);
        System.out.println(name);
        setCellData("pass", 1, 3);

        String str = "learn.testng.DataProviderWithExcel_001@5c3bd550";
        int i = str.indexOf("@");
        System.out.println(i);
        String value = str.substring(0, i);
        int j = str.lastIndexOf(".");
        String value1 = value.substring(j + 1);
        System.out.println(value1);

        System.out.println(getTestCaseName(str));

        int num = getRowContains(value1, 0);
        System.out.println(num);
    }
}
