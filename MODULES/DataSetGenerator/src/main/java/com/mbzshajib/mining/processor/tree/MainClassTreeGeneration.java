package com.mbzshajib.mining.processor.tree;

import com.mbzshajib.mining.Initializer;
import com.mbzshajib.mining.processor.tree.initial.TreeGenerator;
import com.mbzshajib.mining.processor.tree.initial.TreeInput;
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
        TreeInput treeInput = getTreeInput();
        Utils.log(TAG, treeInput.toString());
        Processor processor = new TreeGenerator();
        processor.process(treeInput);
    }

    private static TreeInput getTreeInput() {
        TreeInput treeInput = new TreeInput();
        treeInput.setInputFilePath("INPUT/uncertain_mushrooms_20092015.txt");
        treeInput.setFrameSize(10);
        treeInput.setWindowSize(5);
        return treeInput;
    }
}
