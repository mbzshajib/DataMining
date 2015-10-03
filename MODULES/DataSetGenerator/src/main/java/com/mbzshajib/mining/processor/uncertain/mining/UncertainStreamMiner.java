package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.util.Constant;
import com.mbzshajib.mining.util.Utils;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/28/2015
 * @time: 7:46 PM
 * ****************************************************************
 */

public class UncertainStreamMiner implements Processor<UncertainStreamMineInput, UncertainStreamMineOutput> {
    public static final String TAG = UncertainStreamMiner.class.getCanonicalName();

    @Override
    public UncertainStreamMineOutput process(UncertainStreamMineInput uncertainStreamMineInput) throws ProcessingError {
        printBeforeMining(uncertainStreamMineInput);
        return null;
    }

    private void printBeforeMining(UncertainStreamMineInput uncertainStreamMineInput) {
        UncertainTree uncertainTree = uncertainStreamMineInput.getUncertainTree();
        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, "Tree For Mining");
        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, uncertainTree.getTraversedString());
        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, "Mining Started");
        Utils.log(Constant.MULTI_STAR);
    }
}
