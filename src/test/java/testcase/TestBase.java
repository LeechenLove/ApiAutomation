package testcase;

import beans.BaseBean;
import utility.ExcleUtils1;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/21 9:55
 **/
public class TestBase {

    protected <T extends BaseBean> List<T> readExcelData(Class<T> clz,
                                                         String[] excelPathArr, String[] sheetNameArr) {
        List<T> allExcelData = new ArrayList<T>();// excel文件數組
        List<T> temArrayList = new ArrayList<T>();
        for (String excelPath : excelPathArr) {
            File file = Paths.get(System.getProperty("user.dir"),
                    excelPath).toFile();
            temArrayList.clear();
            if (sheetNameArr.length == 0 || sheetNameArr[0] == "") {
                temArrayList = ExcleUtils1.readExcel(clz, file.getAbsolutePath());
            } else {
                for (String sheetName : sheetNameArr) {
                    temArrayList.addAll(ExcleUtils1.readExcel(clz,
                            file.getAbsolutePath(), sheetName));
                }
            }
            temArrayList.forEach((bean) -> {
                bean.setExcelName(file.getName());
            });
            allExcelData.addAll(temArrayList); // 将excel数据添加至list
        }
        return allExcelData;
    }
}
