package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.utility.model.Output;
import com.mbzshajib.utility.model.fpatterns.FNode;

import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/3/2015
 * @time: 9:30 PM
 * ****************************************************************
 */

public class UncertainStreamMineOutput implements Output {
    private TimeModel miningTime;
    private List<FrequentItem> frequentItemList;
    private FNode frequentNodesRoot;

    public TimeModel getMiningTime() {
        return miningTime;
    }

    public void setMiningTime(TimeModel miningTime) {
        this.miningTime = miningTime;
    }

    public List<FrequentItem> getFrequentItemList() {
        return frequentItemList;
    }

    public void setFrequentItemList(List<FrequentItem> frequentItemList) {
        this.frequentItemList = frequentItemList;
    }

    public FNode getFrequentNodesRoot() {
        return frequentNodesRoot;
    }

    public void setFrequentNodesRoot(FNode frequentNodesRoot) {
        this.frequentNodesRoot = frequentNodesRoot;
    }

    @Override
    public String toString() {
        return "UncertainStreamMineOutput{" +
                "miningTime=" + miningTime +
                ", frequentItemList=" + frequentItemList +
                '}';
    }
}
