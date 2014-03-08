package com.linchproject.framework;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Georg Schmidl
 */
public class ResourceBundleI18n implements I18n {

    List<ResourceBundle> resourceBundles;

    public ResourceBundleI18n(List<ResourceBundle> resourceBundles) {
        this.resourceBundles = resourceBundles;
    }

    public String getText(String key) {
        String text = key;
        for (ResourceBundle resourceBundle : resourceBundles) {
            try {
                text = resourceBundle.getString(key);
                break;
            } catch (MissingResourceException e) {
                // ignore
            }
        }
        return text;
    }

}
