package pojo.request;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/13 22:00
 **/
public class AuthorizationRequest {

    public String userName;
    public String password;

    public AuthorizationRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
