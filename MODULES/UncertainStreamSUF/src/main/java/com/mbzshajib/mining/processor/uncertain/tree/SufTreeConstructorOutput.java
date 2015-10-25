package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.model.SufInputData;
import com.mbzshajib.mining.processor.uncertain.model.SufTree;
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
 * @date: 10/21/2015
 * @time: 7:45 PM
 * ****************************************************************
 */

public class SufTreeConstructorOutput implements Output {
    private SufTree sufTree;
    private TimeModel treeConstructionTime;
    private TimeModel scanningTransactionTime;
    private List<List<SufInputData>> windowTransactionList;

    public SufTree getSufTree() {
        return sufTree;
    }

    public void setSufTree(SufTree sufTree) {
        this.sufTree = sufTree;
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

    public List<List<SufInputData>> getWindowTransactionList() {
        return windowTransactionList;
    }

    public void setWindowTransactionList(List<List<SufInputData>> windowTransactionList) {
        this.windowTransactionList = windowTransactionList;
    }
}
