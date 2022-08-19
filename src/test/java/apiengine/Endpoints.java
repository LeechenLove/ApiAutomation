package apiengine;

import configs.ConfigReader;
import configs.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import pojo.request.AddBooksRequest;
import pojo.request.AuthorizationRequest;
import pojo.request.RemoveBookRequest;
import pojo.response.Books;
import pojo.response.Token;
import pojo.response.UserAccount;


/**
 * @Author: Lulu
 * @Description: 封装调用api的方法
 * @DateTime: 2022/8/14 15:50
 **/
public class Endpoints {
    private final RequestSpecification request;

    public Endpoints(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
        request.header("Content-Type", Constants.CONTENT_TYPE_JSON);
    }

    public void authenticateUser(AuthorizationRequest authRequest) {
        Response response = request.body(authRequest).post(Route.generateToken());
        if (response.statusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException("Authentication Failed. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        }
        Token tokenResponse = response.getBody().jsonPath().getObject("$", Token.class);
        request.header("Authorization", "Bearer " + tokenResponse.token);
    }

    public IRestResponse<Books> getBooks() {
        Response response = request.get(Route.books());
        return new RestResponse(Books.class, response);
    }

    public IRestResponse<UserAccount> addBook(AddBooksRequest addBooksRequest) {
        Response response = request.body(addBooksRequest).post(Route.books());
        return new RestResponse(UserAccount.class, response);
    }

    public Response removeBook(RemoveBookRequest removeBookRequest) {
        return request.body(removeBookRequest).delete(Route.book());
    }

    public IRestResponse<UserAccount> getUserAccount(String userId) {
        Response response = request.get(Route.userAccount(userId));
        return new RestResponse(UserAccount.class, response);
    }
}
