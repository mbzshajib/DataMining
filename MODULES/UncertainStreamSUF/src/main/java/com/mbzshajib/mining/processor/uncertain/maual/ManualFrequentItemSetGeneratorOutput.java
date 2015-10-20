package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.utility.model.Output;

import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/5/2015
 * @time: 8:33 PM
 * ****************************************************************
 */

public class ManualFrequentItemSetGeneratorOutput implements Output {
    private TimeModel miningTime;
    private List<FrequentItem> frequentItemList;

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

    @Override
    public String toString() {
        return "ManualFrequentItemSetGeneratorOutput{" +
                "miningTime=" + miningTime +
                ", frequentItemList=" + frequentItemList +
                '}';
    }
}
