package testcase;

import beans.ExcelDataBean;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/21 9:50
 **/
public class TestExcel extends TestBase{

    protected List<ExcelDataBean> dataList = new ArrayList<>();

    @Parameters({"excelPath", "sheetName"})
    @BeforeTest
    public void readData(String excelPath, String sheetName) {
        dataList = readExcelData(ExcelDataBean.class, excelPath.split(";"), sheetName.split(";"));
        System.out.println(dataList);
    }

    @Test(dataProvider = "apiDatas")
    public void test(ExcelDataBean excelDataBean) {
        System.out.println(excelDataBean.getDesc());
        System.out.println(excelDataBean.getMethod());
    }

    @DataProvider(name = "apiDatas")
    public Iterator<Object[]> getApiData(ITestContext context) {
        List<Object[]> dataProvider = new ArrayList<Object[]>();
        for (ExcelDataBean data : dataList) {
            if (data.isRun()) {
                dataProvider.add(new Object[] { data });
            }
        }
        return dataProvider.iterator();
    }
}
