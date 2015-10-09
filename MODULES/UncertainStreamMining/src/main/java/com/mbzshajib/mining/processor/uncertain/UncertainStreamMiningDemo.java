package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.processor.uncertain.uncertaintree.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.uncertaintree.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.uncertaintree.TreeGenerator;
import com.mbzshajib.mining.processor.uncertain.uncertaintree.WindowCompletionCallBackImpl;
import com.mbzshajib.mining.util.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
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
 * @date: 9/20/2015
 * @time: 11:02 PM
 * ****************************************************************
 */

public class UncertainStreamMiningDemo {
    public static final String TAG = UncertainStreamMiningDemo.class.getCanonicalName();
    private static final double MIN_SUP = .5;
    private static int windowNumber = 1;

    public static void main(String[] args) throws ProcessingError, IOException, DataNotValidException {
        ConfigurationLoader<MiningInput> configurationLoader = new ConfigurationLoader<>(MiningInput.class);
        MiningInput miningInput = configurationLoader.loadConfigDataFromJsonFile(new File(Constants.F_MINING_PATH + Constants.F_MINING_FILE));

        TreeConstructionInput treeConstructionInput = getTreeInput(miningInput);

        TreeGenerator processor = new TreeGenerator();
        TreeConstructionOutput treeConstructionOutput = processor.process(treeConstructionInput);
        UncertainTree tree = treeConstructionOutput.getUncertainTree();


    }


    private static TreeConstructionInput getTreeInput(MiningInput miningInput) {
        final TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        treeConstructionInput.setInputFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());
        treeConstructionInput.setFrameSize(miningInput.getFrameSize());
        treeConstructionInput.setWindowSize(miningInput.getWindowSize());
        treeConstructionInput.setWindowCompletionCallback(new WindowCompletionCallBackImpl(miningInput));
        return treeConstructionInput;
    }


}
