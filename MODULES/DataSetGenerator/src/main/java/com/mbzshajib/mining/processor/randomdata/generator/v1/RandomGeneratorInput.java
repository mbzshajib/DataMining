package com.mbzshajib.mining.processor.randomdata.generator.v1;

import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/13/2015
 * @time: 7:55 PM
 * ****************************************************************
 */

public class RandomGeneratorInput implements Input {
    private int totalInput;
    private double minRandValue;
    private double maxRandValue;
    private double lowerPeakValue;
    private double upperPeakValue;
    private byte peakValuePercentage;
    private byte precision;
    public int getTotalInput() {
        return totalInput;
    }

    public void setTotalInput(int totalInput) {
        this.totalInput = totalInput;
    }

    public double getMinRandValue() {
        return minRandValue;
    }

    public void setMinRandValue(double minRandValue) {
        this.minRandValue = minRandValue;
    }

    public double getMaxRandValue() {
        return maxRandValue;
    }

    public void setMaxRandValue(double maxRandValue) {
        this.maxRandValue = maxRandValue;
    }

    public double getLowerPeakValue() {
        return lowerPeakValue;
    }

    public void setLowerPeakValue(double lowerPeakValue) {
        this.lowerPeakValue = lowerPeakValue;
    }

    public double getUpperPeakValue() {
        return upperPeakValue;
    }

    public void setUpperPeakValue(double upperPeakValue) {
        this.upperPeakValue = upperPeakValue;
    }

    public byte getPeakValuePercentage() {
        return peakValuePercentage;
    }

    public void setPeakValuePercentage(byte peakValuePercentage) {
        this.peakValuePercentage = peakValuePercentage;
    }

    public byte getPrecision() {
        return precision;
    }

    public void setPrecision(byte precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "RandomGeneratorInput{" +
                "totalInput=" + totalInput +
                ", minRandValue=" + minRandValue +
                ", maxRandValue=" + maxRandValue +
                ", lowerPeakValue=" + lowerPeakValue +
                ", upperPeakValue=" + upperPeakValue +
                ", peakValuePercentage=" + peakValuePercentage +
                ", precision=" + precision +
                '}';
    }
}
