package by.chyrkun.training.service.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    en_US(ResourceBundle.getBundle("messages", new Locale("en", "US"),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))),
    ru_RU(ResourceBundle.getBundle("messages", new Locale("ru", "RU"))),
    be_BY(ResourceBundle.getBundle("messages", new Locale("be", "BY")));
    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle){
        this.bundle = bundle;
    }

    public String getMessage(String key){
        return bundle.getString(key);
    }

}
