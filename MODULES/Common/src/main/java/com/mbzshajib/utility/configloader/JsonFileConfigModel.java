package com.mbzshajib.utility.configloader;

import java.io.File;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 2:13 PM
 * ****************************************************************
 */


public abstract class JsonFileConfigModel implements ConfigModel {
    private File file;

    public JsonFileConfigModel(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }


}
