package com.mbzshajib.mining.processor.randomdata.generator.v1;

import com.mbzshajib.utility.common.PrintUtils;
import com.mbzshajib.utility.model.Output;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/13/2015
 * @time: 7:57 PM
 * ****************************************************************
 */

public class RandomGeneratorOutput implements Output {
    private double[] lowerValues;
    private double[] peakValues;
    private double[] upperValues;

    public double[] getLowerValues() {
        return lowerValues;
    }

    public void setLowerValues(double[] lowerValues) {
        this.lowerValues = lowerValues;
    }

    public double[] getPeakValues() {
        return peakValues;
    }

    public void setPeakValues(double[] peakValues) {
        this.peakValues = peakValues;
    }

    public double[] getUpperValues() {
        return upperValues;
    }

    public void setUpperValues(double[] upperValues) {
        this.upperValues = upperValues;
    }

    public double[] getAllValues() {
        double[] result = new double[lowerValues.length + peakValues.length + upperValues.length];
        System.arraycopy(lowerValues, 0, result, 0, lowerValues.length);
        System.arraycopy(peakValues, 0, result, lowerValues.length, peakValues.length);
        System.arraycopy(upperValues, 0, result, lowerValues.length + peakValues.length, upperValues.length);
        return result;
    }

    @Override
    public String toString() {
        return "RandomGeneratorOutput{" +
                "lowerValues=" + PrintUtils.getDoubleArrayPrintedValues(lowerValues) +
                ", peakValues=" + PrintUtils.getDoubleArrayPrintedValues(peakValues) +
                ", upperValues=" + PrintUtils.getDoubleArrayPrintedValues(upperValues) +
                '}';
    }
}
