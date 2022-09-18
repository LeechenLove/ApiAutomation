package apiengine;

import io.restassured.response.Response;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/14 16:57
 **/
public interface IRestResponse<T> {
    public T getBody();

    public String getContent();

    public int getStatusCode();

    public String getStatusDescription();

    public boolean isSuccessful();

    public Response getResponse();

    public Exception getException();
}
