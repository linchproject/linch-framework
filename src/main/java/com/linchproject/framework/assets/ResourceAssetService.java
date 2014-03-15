package com.linchproject.framework.assets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public class ResourceAssetService implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(ResourceAssetService.class);

    protected ClassLoader classLoader;

    @Override
    public InputStream getInputStream(String fileName) {
        fileName = "/assets/" + fileName;
        log.debug("loading asset {}", fileName);

        InputStream inputStream;
        if (classLoader != null) {
            log.debug("using {}", classLoader);
            inputStream = classLoader.getResourceAsStream(fileName);
        } else {
            log.debug("using {}", getClass().getClassLoader());
            inputStream = getClass().getResourceAsStream(fileName);
        }
        return inputStream;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
