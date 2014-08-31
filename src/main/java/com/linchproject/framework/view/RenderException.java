package com.linchproject.framework.view;

import com.linchproject.framework.FrameworkException;

/**
 * @author Georg Schmidl
 */
public class RenderException extends FrameworkException {

    public RenderException() {
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }

    public RenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
