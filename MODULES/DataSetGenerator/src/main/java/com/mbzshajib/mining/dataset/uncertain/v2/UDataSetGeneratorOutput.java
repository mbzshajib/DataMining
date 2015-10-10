package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.model.Output;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:18 AM
 * ****************************************************************
 */


public class UDataSetGeneratorOutput implements Output{
    private String pathDataSetGenerated;
    private String nameOfDataSet;
    private int totalTransaction;

    public String getPathDataSetGenerated() {
        return pathDataSetGenerated;
    }

    public void setPathDataSetGenerated(String pathDataSetGenerated) {
        this.pathDataSetGenerated = pathDataSetGenerated;
    }

    public String getNameOfDataSet() {
        return nameOfDataSet;
    }

    public void setNameOfDataSet(String nameOfDataSet) {
        this.nameOfDataSet = nameOfDataSet;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(int totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    @Override
    public String toString() {
        return "UDataSetGeneratorOutput{" +
                "pathDataSetGenerated='" + pathDataSetGenerated + '\'' +
                ", nameOfDataSet='" + nameOfDataSet + '\'' +
                ", totalTransaction=" + totalTransaction +
                '}';
    }
}
