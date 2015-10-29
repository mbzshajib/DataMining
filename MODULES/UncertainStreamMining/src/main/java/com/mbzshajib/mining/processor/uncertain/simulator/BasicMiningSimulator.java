package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.processor.uncertain.callback.WindowCompletionCallBackImpl;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorInput;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.mining.util.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.file.Utility;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.*;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/14/2015
 * @time: 11:54 PM
 * ****************************************************************
 */

public class BasicMiningSimulator {
    public static String CURR_TIME = Utility.getDateTimeString();
    public static final String TAG = BasicMiningSimulator.class.getCanonicalName();
    private static final double MIN_SUP = .5;
    private static int windowNumber = 1;

    public static void main(String[] args) throws ProcessingError, IOException, DataNotFoundException {
        ConfigurationLoader<MiningInput> configurationLoader = new ConfigurationLoader<>(MiningInput.class);
        MiningInput miningInput = configurationLoader.loadConfigDataFromJsonFile(new File(Constants.F_MINING_PATH + Constants.F_MINING_FILE));

        double minSup = .9;
        miningInput.setDataSetName("suf-growth.txt");
        miningInput.setWindowSize(2);
        miningInput.setFrameSize(3);
        miningInput.setMinSupport(minSup);
        TreeConstructionInput treeConstructionInput = getTreeInput(miningInput);
        TreeGenerator processor = new TreeGenerator();
        TreeConstructionOutput treeConstructionOutput = processor.process(treeConstructionInput);
        UncertainTree tree = treeConstructionOutput.getUncertainTree();
//        Evalutor evalutor = new Evalutor();
//        evalutor.process(getEvalutorInput(miningInput));
//        treeConstructionInput.getBufferedReader().close();
    }

    private static EvalutorInput getEvalutorInput(MiningInput miningInput) {
        EvalutorInput input = new EvalutorInput();
        input.setDataSetName("Puff");
        input.setResultFileName("Puff" + CURR_TIME + ".result");
        input.setFindFalseNegative(false);
        input.setMetaDataName(miningInput.getMetaDataFile());
        input.setMiningMetaDataPath(miningInput.getMetaDataPath());
        input.setMiningDataSetFileName(miningInput.getDataSetName());
        input.setMiningDataSetPath(miningInput.getDataSetPath());
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
