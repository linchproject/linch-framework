package com.linchproject.framework.assets;

import java.io.InputStream;
import java.util.Date;

/**
 * @author Georg Schmidl
 */
public class Asset {

    private String fileName;
    private InputStream inputStream;
    private int length = -1;
    private Date lastModified;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
