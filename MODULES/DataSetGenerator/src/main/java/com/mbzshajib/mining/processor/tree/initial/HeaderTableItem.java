package com.mbzshajib.mining.processor.tree.initial;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/21/2015
 * time: 3:59 PM
 * ****************************************************************
 */


public class HeaderTableItem {
    private int groupSize;
    private String itemId;
    private double[] prefixValues;
    private double support;

    public HeaderTableItem(int groupSize) {
        this.groupSize = groupSize;
        prefixValues = new double[groupSize];
    }

}
