package com.linchproject.framework.components;

import com.linchproject.framework.I18n;
import com.linchproject.framework.ResourceBundleI18n;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author Georg Schmidl
 */
public class ResourceBundleI18nService implements I18nService {

    private static final String I18N_BASE_NAME = "i18n";

    protected ClassLoader classLoader;

    @Override
    public I18n getI18n(Locale locale) {
        List<ResourceBundle> resourceBundles = new ArrayList<ResourceBundle>();

        if (classLoader != null) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle(I18N_BASE_NAME, locale, classLoader);
                resourceBundles.add(resourceBundle);
            } catch (MissingResourceException e) {
                // ignore
            }
        }

        URLClassLoader contextClassLoader = (URLClassLoader)Thread.currentThread().getContextClassLoader();
        for (URL url : contextClassLoader.getURLs()) {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle(I18N_BASE_NAME, locale, urlClassLoader);
                resourceBundles.add(resourceBundle);
            } catch (MissingResourceException e) {
                // ignore
            }
        }
        return new ResourceBundleI18n(resourceBundles);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
