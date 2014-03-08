package com.linchproject.framework.assets;

import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public class ResourceAssetService implements AssetService {

    protected ClassLoader classLoader;

    @Override
    public InputStream getInputStream(String fileName) {
        fileName = "/assets/" + fileName;

        InputStream inputStream;
        if (classLoader != null) {
            inputStream = classLoader.getResourceAsStream(fileName);
        } else {
            inputStream = getClass().getResourceAsStream(fileName);
        }
        return inputStream;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
