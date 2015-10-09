package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.utility.configloader.JsonFileConfigModel;

import java.io.File;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 5:49 PM
 * ****************************************************************
 */


public class MiningInput extends JsonFileConfigModel {
    double minSupport;
    int windowSize;
    int frameSize;
    String dataSetPath;
    String dataSetName;
    String frequentSetPath;
    String frequentSetName;

    public MiningInput(File file) {
        super(file);
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public String getDataSetPath() {
        return dataSetPath;
    }

    public void setDataSetPath(String dataSetPath) {
        this.dataSetPath = dataSetPath;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getFrequentSetPath() {
        return frequentSetPath;
    }

    public void setFrequentSetPath(String frequentSetPath) {
        this.frequentSetPath = frequentSetPath;
    }

    public String getFrequentSetName() {
        return frequentSetName;
    }

    public void setFrequentSetName(String frequentSetName) {
        this.frequentSetName = frequentSetName;
    }

    @Override
    public String toString() {
        return "MiningInput{" +
                "windowSize=" + windowSize +
                ", frameSize=" + frameSize +
                ", dataSetPath='" + dataSetPath + '\'' +
                ", dataSetName='" + dataSetName + '\'' +
                ", frequentSetPath='" + frequentSetPath + '\'' +
                ", frequentSetName='" + frequentSetName + '\'' +
                '}';
    }
}
