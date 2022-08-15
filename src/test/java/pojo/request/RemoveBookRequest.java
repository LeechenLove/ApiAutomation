package pojo.request;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/13 22:04
 **/
public class RemoveBookRequest {
    public String isbn;
    public String userId;

    public RemoveBookRequest(String userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }
}
