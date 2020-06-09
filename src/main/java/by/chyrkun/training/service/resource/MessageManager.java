package by.chyrkun.training.service.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("messages", new Locale("en", "US"),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))),
    RU(ResourceBundle.getBundle("messages", new Locale("ru", "RU"))),
    BY(ResourceBundle.getBundle("messages", new Locale("by", "BY")));

    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle){
        this.bundle = bundle;
    }

    public String getMessage(String key){
        return bundle.getString(key);
    }

}
