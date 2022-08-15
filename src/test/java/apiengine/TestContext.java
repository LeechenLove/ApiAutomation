package apiengine;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/14 21:24
 **/
public class TestContext {
    private String BASE_URL = "https://bookstore.toolsqa.com";
    private Endpoints endPoints;

    public TestContext() {
        endPoints = new Endpoints(BASE_URL);
    }

    public Endpoints getEndPoints() {
        return endPoints;
    }
}
