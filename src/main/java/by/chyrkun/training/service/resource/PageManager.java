package by.chyrkun.training.service.resource;

import java.util.ResourceBundle;

/**
 * Class for getting specific pages from bundle.
 */
public class PageManager {
    /** A resource bundle. */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("pages");
    private PageManager() {}

    /**
     * Gets name of page. Returns page from bundle.
     *
     * @param key the name of page
     * @return the string of page
     */
    public static String getPage(String key){
        return resourceBundle.getString(key);
    }
}
