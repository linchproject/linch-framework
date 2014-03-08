package com.linchproject.framework;

import java.util.HashMap;

/**
 * @author Georg Schmidl
 */
public class Context extends HashMap<String, Object> {

    @Override
    public Context put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
