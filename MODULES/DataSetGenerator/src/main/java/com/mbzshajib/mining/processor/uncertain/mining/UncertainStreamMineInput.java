package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.utility.model.Input;

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

public class UncertainStreamMineInput implements Input {
    private double minSupport;
    private UncertainTree uncertainTree;

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public UncertainTree getUncertainTree() {
        return uncertainTree;
    }

    public void setUncertainTree(UncertainTree uncertainTree) {
        this.uncertainTree = uncertainTree;
    }
}
