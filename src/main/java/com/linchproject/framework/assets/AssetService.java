package com.linchproject.framework.assets;

import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public interface AssetService {

    InputStream getInputStream(String fileName);
}
