package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.utility.configloader.JsonFileConfigModel;

import java.io.File;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/10/2015
 * @time: 11:19 PM
 * ****************************************************************
 */

public class ManualMiningOutput extends JsonFileConfigModel {
    private double minSup;
    private int windowNo;
    private int windowSize;
    private int frameSize;

    private String dataSetFilePath;

    private int frequentItemSize;
    private List<FrequentItem> frequentItemFound;

    private TimeModel miningTime;

    public ManualMiningOutput(File file) {
        super(file);
    }

    public double getMinSup() {
        return minSup;
    }

    public void setMinSup(double minSup) {
        this.minSup = minSup;
    }

    public int getWindowNo() {
        return windowNo;
    }

    public void setWindowNo(int windowNo) {
        this.windowNo = windowNo;
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

    public String getDataSetFilePath() {
        return dataSetFilePath;
    }

    public void setDataSetFilePath(String dataSetFilePath) {
        this.dataSetFilePath = dataSetFilePath;
    }

    public int getFrequentItemSize() {
        return frequentItemSize;
    }

    public void setFrequentItemSize(int frequentItemSize) {
        this.frequentItemSize = frequentItemSize;
    }

    public List<FrequentItem> getFrequentItemFound() {
        return frequentItemFound;
    }

    public void setFrequentItemFound(List<FrequentItem> frequentItemFound) {
        this.frequentItemFound = frequentItemFound;
    }

    public TimeModel getMiningTime() {
        return miningTime;
    }

    public void setMiningTime(TimeModel miningTime) {
        this.miningTime = miningTime;
    }
}
