package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.MiningInput;
import com.mbzshajib.mining.processor.uncertain.MiningOutput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineOutput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMiner;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.File;
import java.io.IOException;

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
        UncertainStreamMineInput uncertainStreamMineInput = getMiningInput(treeConstructionOutput);
        UncertainStreamMiner uncertainStreamMiner = new UncertainStreamMiner();
        UncertainStreamMineOutput miningResult = uncertainStreamMiner.process(uncertainStreamMineInput);
        StringBuilder builder = new StringBuilder();
        String path = miningInput.getFrequentSetPath() + "USM\\";
        builder.append(miningInput.getFrequentSetName())
                .append(Constants.UNDER_SCORE)
                .append("MIN_SUP")
                .append(Constants.HIGH_PHEN)
                .append(miningInput.getMinSupport())
                .append(Constants.UNDER_SCORE)
                .append("WIN_SIZE")
                .append(Constants.HIGH_PHEN)
                .append(miningInput.getWindowSize())
                .append(Constants.UNDER_SCORE)
                .append("FRAME_SIZE")
                .append(Constants.HIGH_PHEN)
                .append(miningInput.getFrameSize())
                .append(Constants.UNDER_SCORE).append(Constants.UNDER_SCORE).append(Constants.UNDER_SCORE)
                .append(++windowNumber);
        MiningOutput miningOutput = new MiningOutput(new File(path + builder.toString()));
        miningOutput.setWindowNo(windowNumber);
        miningOutput.setFrameSize(miningInput.getFrameSize());
        miningOutput.setWindowSize(miningInput.getWindowSize());
        miningOutput.setMiningTime(miningResult.getMiningTime());
        miningOutput.setMinSupport(miningInput.getMinSupport());
        miningOutput.setDataSetFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());

        miningOutput.setFrequentItemSize(miningResult.getFrequentItemList().size());
        miningOutput.setFrequentItemFound(miningResult.getFrequentItemList());

        miningOutput.setMiningTime(miningResult.getMiningTime());
        miningOutput.setTreeConstructionTime(treeConstructionOutput.getTreeConstructionTime());
        miningOutput.setScanningTransactionTime(treeConstructionOutput.getScanningTransactionTime());

        ConfigurationLoader<MiningOutput> loader = new ConfigurationLoader<MiningOutput>(MiningOutput.class);
        printMessage(treeConstructionOutput, miningResult);
        try {
            loader.generateJsonConfigFile(path, builder.toString() + ".json", miningOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printMessage(TreeConstructionOutput treeConstructionOutput, UncertainStreamMineOutput miningOutput) {
        StringBuilder builder = new StringBuilder();
        builder.append("USM_MINING completed for window => ")
                .append(windowNumber)
                .append(Constants.TABBED_HASH)
                .append("Tree construction time-> ")
                .append(treeConstructionOutput.getTreeConstructionTime().getTimeNeeded() - treeConstructionOutput.getScanningTransactionTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("DB scanning time-> ")
                .append(treeConstructionOutput.getScanningTransactionTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("Total mining time-> ")
                .append(miningOutput.getMiningTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("minimum support ")
                .append(miningInput.getMinSupport())
                .append(Constants.TABBED_HASH)
                .append("Found Frequent ItemSets Count ->")
                .append(miningOutput.getFrequentItemList().size())
                .append(Constants.TABBED_HASH)
                .append("Items are -> ")
                .append(miningOutput.getFrequentItemList().toString());
        Logger.log("Window " + windowNumber, builder.toString());
    }

    private UncertainStreamMineInput getMiningInput(TreeConstructionOutput treeConstructionOutput) {
        UncertainStreamMineInput result = new UncertainStreamMineInput();
        result.setMinSupport(miningInput.getMinSupport());
        result.setUncertainTree(treeConstructionOutput.getUncertainTree());
        return result;
    }

}
