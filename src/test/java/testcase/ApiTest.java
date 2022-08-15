package testcase;

import apiengine.IRestResponse;
import apiengine.TestContext;
import configs.ConfigReader;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import pojo.request.AddBooksRequest;
import pojo.request.AuthorizationRequest;
import pojo.request.ISBN;
import pojo.request.RemoveBookRequest;
import pojo.response.Book;
import pojo.response.Books;
import pojo.response.UserAccount;
import org.testng.Assert;
import org.testng.annotations.Test;
import apiengine.Endpoints;

/**
 * @Author: Lulu
 * @Description: 用例层，调用api并进行断言
 * @DateTime: 2022/8/13 22:08
 **/
public class ApiTest {
    private static final String USER_ID = ConfigReader.getInstance().getUserID();
    private static final String USERNAME = "louis4";
    private static final String PASSWORD = "Test@@123";

    private static Response response;
    private static Book book;
    private Endpoints endpoints;

    @BeforeClass
    public void setBaseUrl(){
        TestContext testContext = new TestContext();
        endpoints = testContext.getEndPoints();
    }

    @Test(priority = 1)
    public void iAmAnAuthorizedUser() {
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(USERNAME, PASSWORD);
        endpoints.authenticateUser(authorizationRequest);
    }

    @Test(priority = 2)
    public void listOfBooksAreAvailable() {
        IRestResponse<Books> booksIRestResponse = endpoints.getBooks();
        book = booksIRestResponse.getBody().books.get(0);
        System.out.println(book.author);
    }

    @Test(priority = 3)
    public void addBookInList() {
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);
        IRestResponse<UserAccount> iRestResponse = endpoints.addBook(addBooksRequest);
        Assert.assertEquals(201, iRestResponse.getStatusCode());
        Assert.assertEquals(book.isbn, iRestResponse.getBody().books.get(0).isbn);
    }

    @Test(priority = 4)
    public void removeBookFromList() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);
        response = endpoints.removeBook(removeBookRequest);
    }

    @Test(priority = 5)
    public void bookIsRemoved(){
        Assert.assertEquals(204, response.getStatusCode());

        IRestResponse<UserAccount> iRestResponse = endpoints.getUserAccount(USER_ID);
        Assert.assertEquals(200, iRestResponse.getStatusCode());
        Assert.assertEquals(0, iRestResponse.getBody().books.size());
    }
}
