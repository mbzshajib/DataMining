package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.Initializer;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamDataMiner;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamDataMinerInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamDataMinerOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.mining.util.Utils;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.IOException;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/20/2015
 * time: 11:02 PM
 * ****************************************************************
 */

public class MainClassTreeGeneration {
    public static final String TAG = MainClassTreeGeneration.class.getCanonicalName();

    public static void main(String[] args) throws ProcessingError, IOException {
        Initializer.initialize();
        Utils.log(TAG, "Tree generation algorithm started.");
        TreeConstructionInput treeConstructionInput = getTreeInput();
        Utils.log(TAG, treeConstructionInput.toString());
        TreeGenerator processor = new TreeGenerator();
        TreeConstructionOutput treeConstructionOutput = (TreeConstructionOutput) processor.process(treeConstructionInput);
        Utils.log(TAG, "Start Time " + treeConstructionOutput.getStartTime() + " MS");
        Utils.log(TAG, "End Time " + treeConstructionOutput.getEndTime() + " MS");
        Utils.log(TAG, "Time needed " + (treeConstructionOutput.getEndTime() - treeConstructionOutput.getStartTime()) + " MS");
        Utils.log(TAG, "Constructed Tree " + treeConstructionOutput.getUncertainTree().getRootNode().traverse());
        Utils.log(TAG, "Header Table " + treeConstructionOutput.getUncertainTree().getHeaderTable().traverse());
        UncertainStreamDataMiner dataMiner = new UncertainStreamDataMiner();
        UncertainStreamDataMinerOutput dataMinerOutput = dataMiner.process(getUncertainDataMiningInput(treeConstructionOutput));

    }

    private static UncertainStreamDataMinerInput getUncertainDataMiningInput(TreeConstructionOutput treeConstructionOutput) {
        UncertainStreamDataMinerInput input = new UncertainStreamDataMinerInput();
        input.setUncertainTree(treeConstructionOutput.getUncertainTree());
        input.setMinSupport(.8);
        return input;
    }

    private static TreeConstructionInput getTreeInput() {
        TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        treeConstructionInput.setInputFilePath("INPUT/test_data_22092015.txt");
        treeConstructionInput.setFrameSize(2);
        treeConstructionInput.setWindowSize(3);
        return treeConstructionInput;
    }
}
