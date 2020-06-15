package by.chyrkun.training.dao.db.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public enum AppProperties {

    DB_PROPERTIES;

    private String url;
    private String user;
    private String password;
    private int initialPoolSize = 10;
    private final Logger LOGGER = LogManager.getLogger(AppProperties.class);
    private final ResourceBundle resourceBundle;

    AppProperties() {
        resourceBundle = ResourceBundle.getBundle("application");
        url = resourceBundle.getString("dbUrl");
        user = resourceBundle.getString("dbUser");
        password = resourceBundle.getString("dbPassword");
    }

    public static AppProperties getDbProperties() {
        return DB_PROPERTIES;
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
