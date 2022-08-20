package testcase;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.Order;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/9 11:27
 **/
public class RestAssuredAPITest {

    @Test
    // Response状态码的测试方法
    public void TestResponseStatusCode(){
        // 指定需要访问的网址
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        // 发送get请求，返回response
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Correct status code returned");

        String statusCodeLine = response.getStatusLine();
        Assert.assertEquals(statusCodeLine, "HTTP/1.1 200 OK", "Correct status code line");
    }

    @Test
    // Response header测试方法
    public void TestResponseHeader() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("");

        //获取response的所有header
        Headers allHeaders = response.getHeaders();
        for(Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
    }

    @Test
    // Response body的测试方法
    public void TestResponseBody() {
        // 指定需要访问的网址
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        // 发送get请求，返回response
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/Books");

        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(response.prettyPrint());
        Assert.assertEquals(bodyAsString.contains("9781449325862"), true);
    }

    @Test
    public void VerifyJsonPath() {
        // 指定需要访问的网址
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        // 发送get请求，返回response
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/Books");

        // http://jsonpath.com/
        JsonPath jsonPath = response.jsonPath();
        String isbn = jsonPath.get("books[1].isbn");
        Assert.assertEquals(isbn, "9781449331818");
    }

    @Test
    public void queryParameter() {
        // 指定需要访问的网址
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        // 发送get请求，返回response
        RequestSpecification httpRequest = given();
        Response response = httpRequest.queryParam("ISBN", "9781449331818").get("/Book");

        ResponseBody body = response.body();
        String responseBody = body.asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        String title = jsonPath.getString("title");
        System.out.println(title);
    }

    @Test
    public void postMethod() {
        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3/store";
        RequestSpecification httpRequest = given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", "11");
        requestParams.put("petId", "123456");
        requestParams.put("quantity", "7");
        requestParams.put("shipDate", "2022-08-10T12:04:09.926+00:00");
        requestParams.put("status", "approved");
        requestParams.put("complete", "true");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("/order");
        System.out.println(response.statusLine());
        System.out.println(response.prettyPrint());
    }

    @Test
    public void testDeSerialization() throws JsonProcessingException {
        // 获取response返回的json数据
        String responseBody = given().
                header("Content-Type", "application/json").
                get("https://petstore3.swagger.io/api/v3/store/order/11").
                getBody()
                .asString();
        System.out.println(responseBody);

        //将json数据转化成POJO对象
        ObjectMapper objectMapper1 = new ObjectMapper();
        Order orderData = objectMapper1.readValue(responseBody, Order.class);
        Assert.assertEquals(orderData.getStatus(), "approved");
    }

    @Test
    public void testSerializationUsingPOJO() throws JsonProcessingException {
        // 初始化POJO对象
        Order order = new Order(11, 198772, 8, "2022-08-10T12:04:09.926+00:00", "approved", true);

        // 将POJO对象转化成json格式数据
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
        System.out.println(jsonStr);

        given().
                contentType("application/json").
                body(jsonStr).
                when().
                post("https://petstore3.swagger.io/api/v3/store/order").
                then().
                statusCode(200);
    }


    @Test
    public void testPutMethod() {
        String url = "https://postman-echo.com/put";
        String requestBody = "This is expected to be sent back as part of response body.";

        Response response = given().
                body(requestBody).
                put(url);

        JsonPath jsonPath = response.jsonPath();
        String data = jsonPath.get("data");
        Assert.assertEquals(data, requestBody);
    }

    @Test
    public void testGetMethod() {
        String url = "https://postman-echo.com/get";
        Map<String, String> paramMap = new  HashMap<>();
        paramMap.put("foo1", "bar1");
        paramMap.put("foo2", "bar2");

        Response response = given().
                queryParams(paramMap).
                get(url);

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        String param1 = jsonPath.get("args.foo1");
        String param2 = jsonPath.get("args.foo2");
        Assert.assertEquals(param1, paramMap.get("foo1"));
        Assert.assertEquals(param2, paramMap.get("foo2"));
    }

    @Test
    public void testPostMethod() {
        String url = "https://postman-echo.com/post";

        Map<String, String> paramMap = new  HashMap<>();
        paramMap.put("foo1", "bar1");
        paramMap.put("foo2", "bar2");

        Response response = given().
                body(paramMap).
                post(url);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void testDeleteMethod() {
        String url = "https://postman-echo.com/delete";
        String requestBody = "This is expected to be sent back as part of response body.";

        Response response = given().
                body(requestBody).
                delete(url);

        JsonPath jsonPath = response.jsonPath();
        String data = jsonPath.get("data");
        Assert.assertEquals(data, requestBody);
    }

    @Test
    public void getData() {
        RequestSpecification httpRequest = RestAssured.given().auth().basic("postman", "password");
        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- "+rbdy);
    }

    @Test
    public void RegistrationSuccessful() {
//        RestAssured.baseURI = "https://bookstore.toolsqa.com";
//        RequestSpecification request = RestAssured.given();
//
//        JSONObject requestParams = new JSONObject();
//        /*I have put a unique username and password as below,
//        you can enter any as per your liking. */
//        requestParams.put("userName", "louis2");
//        requestParams.put("password", "Test@@123");
//
//        request.body(requestParams.toJSONString());
//        Response response = request.post("/Account/v1/User");
//        response.prettyPrint();
//
//        Assert.assertEquals(response.getStatusCode(), 201);
//        // We will need the userID in the response body for our tests, please save it in a local variable
//        String userID = response.getBody().jsonPath().getString("userID");

        String url = "https://bookstore.toolsqa.com/Account/v1/User";

        Map<String, String> paramMap = new  HashMap<>();
        paramMap.put("userName", "louis4");
        paramMap.put("password", "Test@@123");

        Response response = given().
                contentType("application/json").
                body(paramMap).
                post(url);
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 201);
        String userID = response.getBody().jsonPath().getString("userID");
        System.out.println(userID);
    }

    @Test
    public void e2eTest() {
        String userID = "454a2f34-96bc-4679-9952-334109821ad2";
        String userName = "louis4";
        String password = "Test@@123";
        String baseUrl = "https://bookstore.toolsqa.com";

        Map<String,String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);

        // 获取user的token
        baseURI = baseUrl;
        Response response = given().
                contentType("application/json").
                body(map).
                post("/Account/v1/GenerateToken");

        String jsonStr = response.asString();
        String token = JsonPath.from(jsonStr).get("token");
        System.out.println(token);

        // 获取book list
        response = given().get("/BookStore/v1/Books");
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonStr = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonStr).get("books");
        System.out.println(books);

        String bookId = books.get(0).get("isbn");
        System.out.println(bookId);

        // 添加book到user
        response = given().
                contentType("application/json").
                header("Authorization", "Bearer " + token).
                body("{ \"userId\": \"" + userID + "\", " +
                        "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
                .post("/BookStore/v1/Books");
        response.prettyPrint();

        response = given().
                contentType("application/json").
                header("Authorization", "Bearer " + token).
                body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + userID + "\"}")
                .delete("/BookStore/v1/Book");
        System.out.println(response.asString());

        response = given().
                contentType("application/json").
                header("Authorization", "Bearer " + token).
                get("/Account/v1/User/" + userID);
        response.prettyPrint();
        jsonStr = response.asString();
        List<Map<String, String>> booksofuser = JsonPath.from(jsonStr).get("books");
        Assert.assertEquals(booksofuser.size(), 0);
    }
}
