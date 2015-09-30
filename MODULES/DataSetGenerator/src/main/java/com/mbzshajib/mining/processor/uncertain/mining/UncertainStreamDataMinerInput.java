package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib 
 * date: 9/30/2015
 * time: 4:13 PM
 * ****************************************************************
 */    

public class UncertainStreamDataMinerInput implements Input {
    private UncertainTree uncertainTree ;
    private double minSupport;

    public UncertainTree getUncertainTree() {
        return uncertainTree;
    }

    public void setUncertainTree(UncertainTree uncertainTree) {
        this.uncertainTree = uncertainTree;
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }
}
