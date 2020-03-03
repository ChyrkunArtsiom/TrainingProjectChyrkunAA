package by.javatr.training.dao.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum AppProperties {

    INSTANCE;

    private String url;
    private String user;
    private String password;
    private int initialPoolSize = 10;

    AppProperties() {
        try(InputStream inputStream = new FileInputStream("./src/by/javatr/training/resources/application.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            url = properties.getProperty("dbUrl");
            user = properties.getProperty("dbUser");
            password = properties.getProperty("dbPassword");

        }catch (IOException ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static AppProperties getInstance() {
        return INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }
}
