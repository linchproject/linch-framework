package com.linchproject.framework;

import com.linchproject.core.Params;
import com.linchproject.core.Result;

import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public abstract class AssetsController extends com.linchproject.core.Controller {

    protected ClassLoader classLoader;

    public Result _(Params params) {
        String fileName = "/assets/" + route.getAfterController();

        InputStream inputStream;
        if (classLoader != null) {
            inputStream = classLoader.getResourceAsStream(fileName);
        } else {
            inputStream = getClass().getResourceAsStream(fileName);
        }
        return binary(inputStream);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
