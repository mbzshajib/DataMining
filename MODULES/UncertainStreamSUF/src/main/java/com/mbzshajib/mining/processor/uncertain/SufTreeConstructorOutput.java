package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.utility.model.Output;

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

    public SufTree getSufTree() {
        return sufTree;
    }

    public void setSufTree(SufTree sufTree) {
        this.sufTree = sufTree;
    }
}
