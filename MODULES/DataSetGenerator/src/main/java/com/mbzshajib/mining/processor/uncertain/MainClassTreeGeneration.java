package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.Initializer;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionInput;
import com.mbzshajib.mining.util.Utils;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

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
        Processor processor = new TreeGenerator();
        processor.process(treeConstructionInput);
    }

    private static TreeConstructionInput getTreeInput() {
        TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        treeConstructionInput.setInputFilePath("INPUT/test_data_22092015.txt");
        treeConstructionInput.setFrameSize(2);
        treeConstructionInput.setWindowSize(3);
        return treeConstructionInput;
    }
}
