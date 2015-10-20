package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.utility.configloader.JsonFileConfigModel;

import java.io.File;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/15/2015
 * @time: 12:08 AM
 * ****************************************************************
 */

public class MiningInputGeneratorConfig extends JsonFileConfigModel {
    private double minSupStart;
    private double minSupEnd;
    private double minSupInterval;
    private int frameStart;
    private int frameEnd;
    private int frameInterval;
    private int windowStart;
    private int windowEnd;
    private int windowInterval;
    private String dataSetName;
    private String dataSetDir;
    private String metaDataFileName;
    private String metaDataFileDir;
    private boolean findFalseNegative;

    public MiningInputGeneratorConfig(File file) {
        super(file);
    }

    public double getMinSupStart() {
        return minSupStart;
    }

    public void setMinSupStart(double minSupStart) {
        this.minSupStart = minSupStart;
    }

    public double getMinSupEnd() {
        return minSupEnd;
    }

    public void setMinSupEnd(double minSupEnd) {
        this.minSupEnd = minSupEnd;
    }

    public double getMinSupInterval() {
        return minSupInterval;
    }

    public void setMinSupInterval(double minSupInterval) {
        this.minSupInterval = minSupInterval;
    }

    public int getFrameStart() {
        return frameStart;
    }

    public void setFrameStart(int frameStart) {
        this.frameStart = frameStart;
    }

    public int getFrameEnd() {
        return frameEnd;
    }

    public void setFrameEnd(int frameEnd) {
        this.frameEnd = frameEnd;
    }

    public int getFrameInterval() {
        return frameInterval;
    }

    public void setFrameInterval(int frameInterval) {
        this.frameInterval = frameInterval;
    }

    public int getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(int windowStart) {
        this.windowStart = windowStart;
    }

    public int getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(int windowEnd) {
        this.windowEnd = windowEnd;
    }

    public int getWindowInterval() {
        return windowInterval;
    }

    public void setWindowInterval(int windowInterval) {
        this.windowInterval = windowInterval;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getDataSetDir() {
        return dataSetDir;
    }

    public void setDataSetDir(String dataSetDir) {
        this.dataSetDir = dataSetDir;
    }

    public String getMetaDataFileName() {
        return metaDataFileName;
    }

    public void setMetaDataFileName(String metaDataFileName) {
        this.metaDataFileName = metaDataFileName;
    }

    public String getMetaDataFileDir() {
        return metaDataFileDir;
    }

    public void setMetaDataFileDir(String metaDataFileDir) {
        this.metaDataFileDir = metaDataFileDir;
    }

    public boolean isFindFalseNegative() {
        return findFalseNegative;
    }

    public void setFindFalseNegative(boolean findFalseNegative) {
        this.findFalseNegative = findFalseNegative;
    }
}
