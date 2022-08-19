package apiengine;

import io.restassured.response.Response;

/**
 * @Author: Lulu
 * @Description: 处理不同response返回的数据的泛型类
 * @DateTime: 2022/8/14 17:01
 **/
public class RestResponse<T> implements IRestResponse<T> {
    private T data;
    private Response response;
    private Exception e;

    public RestResponse(Class<T> t, Response response) {
        this.response = response;
        try {
            this.data = t.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("There should be a default constructor in the Response POJO");

        }
    }

    @Override
    public T getBody() {
        try{
            data = (T) response.getBody().as(data.getClass());
        } catch (Exception e) {
            this.e = e;
        }
        return data;
    }

    @Override
    public String getContent() {
        return response.getBody().asString();
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public String getStatusDescription() {
        return response.getStatusLine();
    }

    @Override
    public boolean isSuccessful() {
        int code = response.getStatusCode();
        return code == 200 || code == 201 || code == 203 || code == 204 || code == 205;
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public Exception getException() {
        return e;
    }
}
