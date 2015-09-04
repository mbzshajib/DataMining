package com.mbzshajib.mining.util;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/4/2015
 * time: 6:43 PM
 * ****************************************************************
 */


public class Configurations {
    public static final String F_DATA_SET_GENERATOR_PROPERTIES = "data_set_generator.properties";
    public static final String K_author_name = "author_name";

    private String authorName ;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
