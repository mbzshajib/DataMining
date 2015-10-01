package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.Initializer;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.mining.processor.uncertain.tree.WindowCompletionCallback;
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
    private static int windowNumber = 1;

    public static void main(String[] args) throws ProcessingError, IOException {
        Initializer.initialize();
        Utils.log(TAG, "Tree generation algorithm started.");
        TreeConstructionInput treeConstructionInput = getTreeInput();
        Utils.log(TAG, treeConstructionInput.toString());
        TreeGenerator processor = new TreeGenerator();
        TreeConstructionOutput treeConstructionOutput = processor.process(treeConstructionInput);
        Utils.log(TAG, "Start Time " + treeConstructionOutput.getStartTime() + " MS");
        Utils.log(TAG, "End Time " + treeConstructionOutput.getEndTime() + " MS");
        Utils.log(TAG, "Time needed " + (treeConstructionOutput.getEndTime() - treeConstructionOutput.getStartTime()) + " MS");
        Utils.log(TAG, "Constructed Tree " + treeConstructionOutput.getUncertainTree().getRootNode().traverse());
        Utils.log(TAG, "Header Table " + treeConstructionOutput.getUncertainTree().getHeaderTable().traverse());

    }

    private static TreeConstructionInput getTreeInput() {
        TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        treeConstructionInput.setInputFilePath("INPUT/test_data_01102015.txt");
        treeConstructionInput.setFrameSize(2);
        treeConstructionInput.setWindowSize(3);
        treeConstructionInput.setWindowCompletionCallback(new WindowCompletionCallback() {
            @Override
            public void windowCompleteUpdate(TreeConstructionOutput treeConstructionOutput) {
                System.out.println("A window has been completed " + windowNumber);
                windowNumber++;
            }
        });
        return treeConstructionInput;
    }


}