package by.chyrkun.training.service.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Enumeration for getting localized messages from bundle.
 */
public enum MessageManager {
    /** The en_US bundle. This is default bundle. */
    en_US(ResourceBundle.getBundle("messages", new Locale("en", "US"),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))),

    /** The ru_RU bundle. */
    ru_RU(ResourceBundle.getBundle("messages", new Locale("ru", "RU"))),

    /** The be_BY bundle. */
    be_BY(ResourceBundle.getBundle("messages", new Locale("be", "BY")));

    /** The bundle field */
    private ResourceBundle bundle;

    /** Constructor for enumeration. Gets bundle. */
    MessageManager(ResourceBundle bundle){
        this.bundle = bundle;
    }

    /**
     * Gets name of message. Returns a message.
     *
     * @param key the name of message
     * @return the string of message
     */
    public String getMessage(String key){
        return bundle.getString(key);
    }

}
