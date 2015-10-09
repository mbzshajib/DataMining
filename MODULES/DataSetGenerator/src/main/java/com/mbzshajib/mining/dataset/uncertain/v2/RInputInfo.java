package com.mbzshajib.mining.dataset.uncertain.v2;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 10:13 AM
 * ****************************************************************
 */


public class RInputInfo {
    private double lowerValue;
    private double upperValue;
    private int weight;
    private byte precision;

    public RInputInfo(double lowerValue, double upperValue, int weight) {
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.weight = weight;
    }

    public byte getPrecision() {
        return precision;
    }

    public void setPrecision(byte precision) {
        this.precision = precision;
    }

    public double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(double lowerValue) {
        this.lowerValue = lowerValue;
    }

    public double getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(double upperValue) {
        this.upperValue = upperValue;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RInputInfo{" +
                "lowerValue=" + lowerValue +
                ", upperValue=" + upperValue +
                ", weight=" + weight +
                '}';
    }
}
