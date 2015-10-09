package com.mbzshajib.mining.processor.uncertain.model;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/30/2015
 * @time: 6:50 PM
 * ****************************************************************
 */

public class UData {
    private double itemProbability;
    private double prefixValue;

    public UData(double itemProbability, double prefixValue) {
        this.itemProbability = itemProbability;
        this.prefixValue = prefixValue;
    }

    public double getItemProbability() {
        return itemProbability;
    }

    public void setItemProbability(double itemProbability) {
        this.itemProbability = itemProbability;
    }

    public double getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(double prefixValue) {
        this.prefixValue = prefixValue;
    }

    @Override
    public String toString() {
        return "UData{" +
                "itemProbability=" + itemProbability +
                ", prefixValue=" + prefixValue +
                '}';
    }

    public UData copy() {
        UData uData = new UData(this.itemProbability, this.prefixValue);
        return uData;
    }
}
