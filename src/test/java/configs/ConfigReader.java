package configs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/14 21:51
 **/
public class ConfigReader {
    private Properties properties;
    private static ConfigReader configReader;

    private ConfigReader(){
        BufferedReader reader;
        String propertyFilePath = "configs//configuration.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try{
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config file not found" + propertyFilePath);
        }
    }

    public static ConfigReader getInstance() {
        if(configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getBaseUrl() {
        String baseUrl = properties.getProperty("base_Url");
        if(baseUrl != null) return baseUrl;
        else throw new RuntimeException("base_url not specified in the Configuration.properties file.");
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
