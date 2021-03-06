package by.chyrkun.training.dao.db.config;

import java.util.ResourceBundle;

/**
 * Enumeration for holding database properties
 */
public enum AppProperties {

    /**
     * Database properties instance.
     */
    DB_PROPERTIES;

    private String url;
    private String user;
    private String password;
    private int initialPoolSize = 10;

    /**
     * Constructor for enumeration. Takes properties from bundle.
     */
    AppProperties() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
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
