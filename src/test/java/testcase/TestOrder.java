package testcase;

import apiengine.IRestResponse;
import beans.ExcelDataBean;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pojo.response.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * @Author: Lulu
 * @Description: 测试订单接口
 * @DateTime: 2022/9/18 18:19
 **/
public class TestOrder extends TestBase{
    protected List<ExcelDataBean> dataList = new ArrayList<>();

    @Parameters({"orderExcelPath", "sheetName"})
    @BeforeClass
    public void readData(String orderExcelPath, String sheetName) {
        dataList = readExcelData(ExcelDataBean.class, orderExcelPath.split(";"), sheetName.split(";"));
//        System.out.println(dataList);
    }

    @Test(dataProvider = "orderData")
    public void test(ExcelDataBean excelDataBean) {
        System.out.println(excelDataBean.getDesc());
        System.out.println(excelDataBean.getMethod());
        System.out.println(excelDataBean.getContentType());
    }

    @Test(dataProvider = "orderData")
    public void postMethod(ExcelDataBean excelDataBean) throws ClassNotFoundException {
        RequestSpecification httpRequest = given();
        httpRequest.header("Content-Type", excelDataBean.getContentType());
        httpRequest.body(excelDataBean.getParam());
        IRestResponse<Order> response = TestBase.returnResponse(
                httpRequest, excelDataBean.getUrl(), excelDataBean.getMethod(), Order.class);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @DataProvider(name = "orderData")
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
