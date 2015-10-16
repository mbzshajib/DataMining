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
 * @time: 7:23 PM
 * ****************************************************************
 */

public class USDMiningOutput extends JsonFileConfigModel {
    private double minSupport;

    private int windowNo;
    private int windowSize;
    private int frameSize;

    private String dataSetFilePath;

    private int totalTreeNode;
    private int frequentItemSize;
    private List<FrequentItem> frequentItemFound;

    private TimeModel treeConstructionTime;
    private TimeModel scanningTransactionTime;
    private TimeModel miningTime;

    public USDMiningOutput(File file) {
        super(file);
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
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

    public int getTotalTreeNode() {
        return totalTreeNode;
    }

    public void setTotalTreeNode(int totalTreeNode) {
        this.totalTreeNode = totalTreeNode;
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

    public TimeModel getTreeConstructionTime() {
        return treeConstructionTime;
    }

    public void setTreeConstructionTime(TimeModel treeConstructionTime) {
        this.treeConstructionTime = treeConstructionTime;
    }

    public TimeModel getScanningTransactionTime() {
        return scanningTransactionTime;
    }

    public void setScanningTransactionTime(TimeModel scanningTransactionTime) {
        this.scanningTransactionTime = scanningTransactionTime;
    }

    public TimeModel getMiningTime() {
        return miningTime;
    }

    public void setMiningTime(TimeModel miningTime) {
        this.miningTime = miningTime;
    }
}
