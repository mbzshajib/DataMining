package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.utility.common.Constants;

import java.text.DecimalFormat;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/15/2015
 * @time: 8:38 PM
 * ****************************************************************
 */

public class Result {

    private String dataSetName;
    private double minSupport;
    private double totalTransactionInTree;

    private int frameSize;
    private int windowSize;

    private double totalFileReadTime;
    private double totalTreeGenerationTime;
    private double totalMiningTime;

    private int falsePositiveCount;
    private int frequentItemSetCount;

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public double getMinSupport() {
        double support = minSupport / frameSize / windowSize * 100;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        support = Double.valueOf(decimalFormat.format(support));
        return support;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public double getTotalTransactionInTree() {
        return totalTransactionInTree;
    }

    public void setTotalTransactionInTree(double totalTransactionInTree) {
        this.totalTransactionInTree = totalTransactionInTree;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public double getTotalFileReadTime() {
        return totalFileReadTime;
    }

    public void setTotalFileReadTime(double totalFileReadTime) {
        this.totalFileReadTime = totalFileReadTime;
    }

    public double getTotalTreeGenerationTime() {
        return totalTreeGenerationTime;
    }

    public void setTotalTreeGenerationTime(double totalTreeGenerationTime) {
        this.totalTreeGenerationTime = totalTreeGenerationTime;
    }

    public double getTotalMiningTime() {
        return totalMiningTime;
    }

    public void setTotalMiningTime(double totalMiningTime) {
        this.totalMiningTime = totalMiningTime;
    }

    public int getFalsePositiveCount() {
        return falsePositiveCount;
    }

    public void setFalsePositiveCount(int falsePositiveCount) {
        this.falsePositiveCount = falsePositiveCount;
    }

    public int getFrequentItemSetCount() {
        return frequentItemSetCount;
    }

    public void setFrequentItemSetCount(int frequentItemSetCount) {
        this.frequentItemSetCount = frequentItemSetCount;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Result{")
                .append("dataSetName='")
                .append(dataSetName)
                .append('\'')
                .append(", minSupport=")
                .append(minSupport)
                .append(", totalTransactionInTree=")
                .append(totalTransactionInTree)
                .append(", frameSize=")
                .append(frameSize)
                .append(", windowSize=")
                .append(windowSize)
                .append(", totalFileReadTime=")
                .append(totalFileReadTime)
                .append(", totalTreeGenerationTime=")
                .append(totalTreeGenerationTime)
                .append(", totalMiningTime=")
                .append(totalMiningTime)
                .append(", falsePositiveCount=")
                .append(falsePositiveCount)
                .append(", frequentItemSetCount=")
                .append(frequentItemSetCount)
                .append('}').toString();
    }

    public String toResult() {
        String string = new StringBuilder()
                .append("Result").append(Constants.TAB)
                .append("dataSetName=")
                .append(dataSetName).append(Constants.TAB)
                .append("minSupport=")
                .append(minSupport).append(" (%)").append(Constants.TAB)
                .append("totalTransactionInTree=")
                .append(totalTransactionInTree).append(Constants.TAB)
                .append("frameSize=")
                .append(frameSize).append(Constants.TAB)
                .append("windowSize=")
                .append(windowSize).append(Constants.TAB)
                .append("totalFileReadTime=")
                .append(totalFileReadTime).append(Constants.TAB)
                .append("totalTreeGenerationTime=")
                .append(totalTreeGenerationTime).append(Constants.TAB)
                .append("totalMiningTime=")
                .append(totalMiningTime).append(Constants.TAB)
                .append("falsePositiveCount=")
                .append(falsePositiveCount).append(Constants.TAB)
                .append("frequentItemSetCount=")
                .append(frequentItemSetCount)
                .append(Constants.NEW_LINE)
                .toString();
        return string;
    }
}
