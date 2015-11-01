package com.mbzshajib.mining.processor.uncertain.infrequent;

import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.utility.model.Input;
import com.mbzshajib.utility.model.fpatterns.FNode;

import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 11/1/2015
 * @time: 6:37 PM
 * ****************************************************************
 */

public class InfrequentRemovarInput implements Input {
    private FNode node;
    private List<List<UInputData>> transactionList;

    public FNode getNode() {
        return node;
    }

    public void setNode(FNode node) {
        this.node = node;
    }

    public List<List<UInputData>> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<List<UInputData>> transactionList) {
        this.transactionList = transactionList;
    }
}
