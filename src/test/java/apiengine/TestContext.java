package apiengine;

import configs.ConfigReader;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/14 21:24
 **/
public class TestContext {
    private String BASE_URL = ConfigReader.getInstance().getBaseUrl();
    private Endpoints endPoints;

    public TestContext() {
        endPoints = new Endpoints(BASE_URL);
    }

    public Endpoints getEndPoints() {
        return endPoints;
    }
}
