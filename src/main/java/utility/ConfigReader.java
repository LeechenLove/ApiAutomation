package utility;

import java.io.*;
import java.util.Properties;

/**
 * @Author: Lulu
 * @Description: 读取配置文件
 * @DateTime: 2022/8/14 21:51
 **/
public class ConfigReader {
    private Properties properties;
    private static ConfigReader configReader;

    //编译后的配置文件
    private ConfigReader(){
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("\\env.properties");
        try {
            try{
                properties = new Properties();
                properties.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigReader getInstance() {
        if(configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getBaseUrl() {
        String baseUrl = properties.getProperty("baseURI");
        if(baseUrl != null) return baseUrl;
        else throw new RuntimeException("base_url not specified in the Configuration.properties file.");
    }

    public String getBasePath() {
        String basePath = properties.getProperty("basePath");
        if(basePath != null) return basePath;
        else throw new RuntimeException("basePath not specified in the Configuration.properties file.");
    }

    public String getPort() {
        String port = properties.getProperty("port");
        if(port != null) return port;
        else throw new RuntimeException("port not specified in the Configuration.properties file.");
    }

    public String getUserID() {
        String userId = properties.getProperty("user_Id");
        if(userId != null) return userId;
        else throw new RuntimeException("user id not specified in the Configuration.properties file.");
    }

    public static void main(String[] args){
        String url = ConfigReader.getInstance().getBaseUrl();
        System.out.println(url);
    }
}
