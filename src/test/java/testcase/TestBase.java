package testcase;

import apiengine.IRestResponse;
import apiengine.RestResponse;
import apiengine.Route;
import beans.BaseBean;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import pojo.response.Books;
import pojo.response.Order;
import utility.ConfigReader;
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
    //封装处理不同request方法

    // 执行测试用例集前的初始化
    @BeforeSuite
    public void dataSetup() {
        System.out.println("执行mysql语句");
    }

    @BeforeClass
    public void envSetup() {
        try{
            RestAssured.baseURI = ConfigReader.getInstance().getBaseUrl();
            RestAssured.basePath = ConfigReader.getInstance().getBasePath();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 根据不同请求方法返回response实体类
    public static IRestResponse returnResponse(
            RequestSpecification request, String url, String method, Class cls) throws ClassNotFoundException {
        if("post".equalsIgnoreCase(method)) {
            Response response = request.post(url);
            return new RestResponse(cls, response);
        } else if ("get".equalsIgnoreCase(method)) {
            Response response = request.get(url);
            return new RestResponse(cls, response);
        } else if ("put".equalsIgnoreCase(method)) {
            Response response = request.put(url);
            return new RestResponse(cls, response);
        } else if ("delete".equalsIgnoreCase(method)) {
            Response response = request.delete(url);
            return new RestResponse(cls, response);
        } else {
            throw new RuntimeException("未获取到任何http方法");
        }
    }

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
