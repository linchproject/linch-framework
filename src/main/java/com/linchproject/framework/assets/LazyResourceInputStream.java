package com.linchproject.framework.assets;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public class LazyResourceInputStream extends InputStream {

    private ClassLoader classLoader;
    private String resourceName;
    
    private InputStream inputStream;

    public LazyResourceInputStream(ClassLoader classLoader, String resourceName) {
        this.classLoader = classLoader;
        this.resourceName = resourceName;
    }
    
    protected InputStream getInputStream() {
        if (inputStream == null) {
            inputStream = classLoader.getResourceAsStream(resourceName);
        }
        return inputStream;
    }

    @Override
    public int read() throws IOException {
        return getInputStream().read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return getInputStream().read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return getInputStream().read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return getInputStream().skip(n);
    }

    @Override
    public int available() throws IOException {
        return getInputStream().available();
    }

    @Override
    public void close() throws IOException {
        getInputStream().close();
    }

    @Override
    public void mark(int readlimit) {
        getInputStream().mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        getInputStream().reset();
    }

    @Override
    public boolean markSupported() {
        return getInputStream().markSupported();
    }
}
