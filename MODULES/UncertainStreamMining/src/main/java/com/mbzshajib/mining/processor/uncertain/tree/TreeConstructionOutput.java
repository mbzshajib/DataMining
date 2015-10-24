package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.utility.model.Output;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:03 PM
 * ****************************************************************
 */

public class TreeConstructionOutput implements Output {
    private UncertainTree uncertainTree;
    private TimeModel treeConstructionTime;
    private TimeModel scanningTransactionTime;
    private List<List<UInputData>> windowTransactionList;

    public UncertainTree getUncertainTree() {
        return uncertainTree;
    }

    public void setUncertainTree(UncertainTree uncertainTree) {
        this.uncertainTree = uncertainTree;
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

    public List<List<UInputData>> getWindowTransactionList() {
        return windowTransactionList;
    }

    public void setWindowTransactionList(List<List<UInputData>> windowTransactionList) {
        this.windowTransactionList = windowTransactionList;
    }

    @Override
    public String toString() {
        return "TreeConstructionOutput{" +
                "uncertainTree=" + uncertainTree +
                ", treeConstructionTime=" + treeConstructionTime +
                ", scanningTransactionTime=" + scanningTransactionTime +
                '}';
    }
}
