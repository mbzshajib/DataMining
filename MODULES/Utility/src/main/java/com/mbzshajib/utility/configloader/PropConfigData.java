package com.mbzshajib.utility.configloader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 11:34 AM
 * ****************************************************************
 */


public abstract class PropConfigData {
    private File file;
    private Map<String, String> properties;

    public PropConfigData(File file) {
        properties = new HashMap<String, String>();
        this.file = file;
    }

    protected File getFile() {
        return file;
    }

    private Map<String, String> getAllProperties() {
        return properties;
    }

    public void setProperty(String key, String value) {
        if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
            this.properties.put(key, value);
        }
    }

    public String getProperty(String key) {
        String result = null;
        if (key != null && !key.isEmpty()) {
            result = this.properties.get(key);
        }
        return result;
    }

    @Override
    public String toString() {
        return "PropertyData{" +
                "file='" + file + '\'' +
                ", properties=" + properties +
                '}';
    }
}
