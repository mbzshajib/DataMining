package com.mbzshajib.mining.processor.uncertain.uncertaintree;

import com.mbzshajib.mining.processor.uncertain.MiningInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMiner;
import com.mbzshajib.utility.model.ProcessingError;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 6:14 PM
 * ****************************************************************
 */


public class WindowCompletionCallBackImpl implements WindowCompletionCallback {
    private int windowNumber;
    private MiningInput miningInput;

    public WindowCompletionCallBackImpl(MiningInput miningInput) {
        this.windowNumber = 0;
        this.miningInput = miningInput;
    }

    @Override
    public void sendUpdate(TreeConstructionOutput treeConstructionOutput) throws ProcessingError {
        windowNumber++;
        UncertainStreamMineInput uncertainStreamMineInput = getMiningInput(treeConstructionOutput);
        UncertainStreamMiner uncertainStreamMiner = new UncertainStreamMiner();
        uncertainStreamMiner.process(uncertainStreamMineInput);
    }

    private UncertainStreamMineInput getMiningInput(TreeConstructionOutput treeConstructionOutput) {
        UncertainStreamMineInput result = new UncertainStreamMineInput();
        result.setMinSupport(miningInput.getMinSupport());
        result.setUncertainTree(treeConstructionOutput.getUncertainTree());
        return result;
    }

}
