package com.mbzshajib.mining.processor.uncertain.simulator;

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
   private double minSupport;
   private int windowSize;
   private int frameSize;
   private String dataSetPath;
   private String dataSetName;
   private String metaDataPath;
   private String metaDataFile;
   private boolean findFalseNegative;

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

    public String getMetaDataPath() {
        return metaDataPath;
    }

    public void setMetaDataPath(String metaDataPath) {
        this.metaDataPath = metaDataPath;
    }

    public String getMetaDataFile() {
        return metaDataFile;
    }

    public void setMetaDataFile(String metaDataFile) {
        this.metaDataFile = metaDataFile;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public boolean isFindFalseNegative() {
        return findFalseNegative;
    }

    public void setFindFalseNegative(boolean findFalseNegative) {
        this.findFalseNegative = findFalseNegative;
    }

    @Override
    public String toString() {
        return "MiningInput{" +
                "minSupport=" + minSupport +
                ", windowSize=" + windowSize +
                ", frameSize=" + frameSize +
                ", dataSetPath='" + dataSetPath + '\'' +
                ", dataSetName='" + dataSetName + '\'' +
                ", metaDataPath='" + metaDataPath + '\'' +
                ", metaDataFile='" + metaDataFile + '\'' +
                ", findFalseNegative=" + findFalseNegative +
                '}';
    }
}
