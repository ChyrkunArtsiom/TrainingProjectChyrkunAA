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

    /*    try(InputStream inputStream = new FileInputStream("./src/main/resources/application.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            url = properties.getProperty("dbUrl");
            user = properties.getProperty("dbUser");
            password = properties.getProperty("dbPassword");

        }catch (IOException ex){
            LOGGER.log(Level.FATAL, "Properties file doesn't exist");
            throw new UncheckedIOException("Properties file doesn't exist", ex);
        }*/
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
