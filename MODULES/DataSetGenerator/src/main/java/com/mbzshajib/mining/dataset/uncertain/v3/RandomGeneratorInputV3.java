package com.mbzshajib.mining.dataset.uncertain.v3;

import com.mbzshajib.utility.callbacks.DataSaver;
import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/12/2015
 * @time: 12:52 PM
 * ****************************************************************
 */


public class RandomGeneratorInputV3 implements Input {
    private int numberOfRandom;
    private String outputFileName;
    private String outputPath;
    private DataSaver dataSaver;
    private double mean;
    private double variance;

    public int getNumberOfRandom() {
        return numberOfRandom;
    }

    public void setNumberOfRandom(int numberOfRandom) {
        this.numberOfRandom = numberOfRandom;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }

    public void setDataSaver(DataSaver dataSaver) {
        this.dataSaver = dataSaver;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }
}
