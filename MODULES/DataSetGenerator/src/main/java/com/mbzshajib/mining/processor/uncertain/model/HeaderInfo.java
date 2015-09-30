package com.mbzshajib.mining.processor.uncertain.model;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/30/2015
 * time: 4:29 PM
 * ****************************************************************
 */

public class HeaderInfo {
    private String id;
    private double prefixValue;
    private double supportValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(double prefixValue) {
        this.prefixValue = prefixValue;
    }

    public double getSupportValue() {
        return supportValue;
    }

    public void setSupportValue(double supportValue) {
        this.supportValue = supportValue;
    }
}
