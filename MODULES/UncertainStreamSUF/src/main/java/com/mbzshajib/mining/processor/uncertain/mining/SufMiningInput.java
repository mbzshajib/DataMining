package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.SufTree;
import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/21/2015
 * @time: 7:56 PM
 * ****************************************************************
 */

public class SufMiningInput implements Input {
    private double minSupport;
    private SufTree sufTree;

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public SufTree getSufTree() {
        return sufTree;
    }

    public void setSufTree(SufTree sufTree) {
        this.sufTree = sufTree;
    }
}
