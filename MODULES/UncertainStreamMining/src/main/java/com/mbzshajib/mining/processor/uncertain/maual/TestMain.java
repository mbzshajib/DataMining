package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.mining.processor.uncertain.MiningInput;
import com.mbzshajib.mining.processor.uncertain.callback.ManualWindowCompletionCallbackImpl;
import com.mbzshajib.mining.util.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.*;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/5/2015
 * @time: 8:36 PM
 * ****************************************************************
 */

public class TestMain {
    private static final int WINDOW_SIZE = 3;
    private static final String FILE_INPUT_PATH = "INPUT/puff_tree_dataset.txt";
    private static final int FRAME_SIZE = 2;
    private static final double MIN_SUPPORT = .5;

    public static void main(String[] args) throws ProcessingError, IOException {
        ConfigurationLoader<MiningInput> miningInputConfigurationLoader = new ConfigurationLoader<MiningInput>(MiningInput.class);
        File file = new File(Constants.F_MINING_PATH + Constants.F_MINING_FILE);
        MiningInput miningInput = miningInputConfigurationLoader.loadConfigDataFromJsonFile(file);
        ManualFrequentItemSetGeneratorInput input = null;
        input = getInput(miningInput);
        ManualFrequentItemSetGenerator generator = new ManualFrequentItemSetGenerator();
        generator.process(input);
    }

    public static ManualFrequentItemSetGeneratorInput getInput(MiningInput miningInput) throws IOException {
        File file = new File(miningInput.getDataSetPath() + miningInput.getDataSetName());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ManualFrequentItemSetGeneratorInput input = new ManualFrequentItemSetGeneratorInput();
        input.setBufferedReader(reader);
        input.setWindowSize(miningInput.getWindowSize());
        input.setFrameSize(miningInput.getFrameSize());
        input.setMinSupport(miningInput.getMinSupport());
        input.setCallback(new ManualWindowCompletionCallbackImpl(miningInput));
        return input;
    }
}
