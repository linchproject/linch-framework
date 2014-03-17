package com.linchproject.framework.assets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Georg Schmidl
 */
public class ResourceAssetService implements AssetService {

    private static final Logger log = LoggerFactory.getLogger(ResourceAssetService.class);
    
    private final Date lastModified = new Date();

    protected ClassLoader classLoader;

    @Override
    public Asset getAsset(String fileName) {
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
        Asset asset = new Asset();
        asset.setFileName(new File(fileName).getName());
        asset.setInputStream(inputStream);
        asset.setLastModified(lastModified);

        return asset;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
