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

        Asset asset = new Asset();
        asset.setFileName(fileName);
        asset.setLastModified(lastModified);

        if (this.classLoader != null) {
            asset.setInputStream(new LazyResourceInputStream(classLoader, fileName));
            log.debug("using {}", classLoader);
        } else {
            asset.setInputStream(new LazyResourceInputStream(getClass(), fileName));
            log.debug("using {}", getClass());
        }

        return asset;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
