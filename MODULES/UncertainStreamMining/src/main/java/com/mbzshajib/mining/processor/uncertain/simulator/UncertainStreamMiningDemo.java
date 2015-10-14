package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.processor.uncertain.callback.WindowCompletionCallBackImpl;
import com.mbzshajib.mining.processor.uncertain.evalutor.Evalutor;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorInput;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.mining.util.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.*;

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

    public static void main(String[] args) throws ProcessingError, IOException, DataNotFoundException {
        ConfigurationLoader<MiningInput> configurationLoader = new ConfigurationLoader<>(MiningInput.class);
        MiningInput miningInput = configurationLoader.loadConfigDataFromJsonFile(new File(Constants.F_MINING_PATH + Constants.F_MINING_FILE));
//        for (int windowSize = 1; windowSize < 50; windowSize += 3) {
//            miningInput.setWindowSize(windowSize);
//            for (int framesize = 10; framesize < 1000; framesize += 30) {
//                miningInput.setFrameSize(framesize);
        for (double i = .01; i < .1; i += .01) {
            TreeConstructionInput treeConstructionInput = getTreeInput(miningInput);
            double minSup = i * miningInput.getFrameSize() * miningInput.getFrameSize()/100;
            miningInput.setMinSupport(minSup);
            TreeGenerator processor = new TreeGenerator();
            TreeConstructionOutput treeConstructionOutput = processor.process(treeConstructionInput);
            UncertainTree tree = treeConstructionOutput.getUncertainTree();
            Evalutor evalutor = new Evalutor();
            evalutor.process(getEvalutorInput(miningInput.getMetaDataPath(), miningInput.getMetaDataFile()));
            treeConstructionInput.getBufferedReader().close();
        }
//            }
//        }


    }

    private static EvalutorInput getEvalutorInput(String metaDataPath, String metaDataFile) {
        EvalutorInput input = new EvalutorInput();
        input.setMetaDataName(metaDataFile);
        input.setMiningMetaDataPath(metaDataPath);
        return input;
    }


    private static TreeConstructionInput getTreeInput(MiningInput miningInput) throws FileNotFoundException {
        final TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        String inputPath = miningInput.getDataSetPath() + miningInput.getDataSetName();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputPath)));
        treeConstructionInput.setBufferedReader(bufferedReader);
        treeConstructionInput.setFrameSize(miningInput.getFrameSize());
        treeConstructionInput.setWindowSize(miningInput.getWindowSize());
        treeConstructionInput.setWindowCompletionCallback(new WindowCompletionCallBackImpl(miningInput));
        return treeConstructionInput;
    }


}
