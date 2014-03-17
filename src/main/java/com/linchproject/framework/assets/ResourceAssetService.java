package com.linchproject.framework.assets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        ClassLoader classLoader;
        if (this.classLoader != null) {
            classLoader = this.classLoader;
        } else {
            classLoader = getClass().getClassLoader();
        }
        log.debug("using {}", classLoader);

        Asset asset = new Asset();
        asset.setFileName(fileName);
        asset.setInputStream(new LazyResourceInputStream(classLoader, fileName));
        asset.setLastModified(lastModified);

        return asset;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
