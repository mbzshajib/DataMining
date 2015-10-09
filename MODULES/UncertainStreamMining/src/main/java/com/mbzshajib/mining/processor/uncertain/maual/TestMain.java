package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.utility.model.ProcessingError;

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
    private static final int WINDOW_SIZE = 4;
    private static final String FILE_INPUT_PATH = "INPUT/puff_tree_dataset.txt";
    private static final int FRAME_SIZE = 1;
    private static final double MIN_SUPPORT = .5;

    public static void main(String[] args) throws ProcessingError {
        ManualFrequentItemSetGeneratorInput input = getInput();
        ManualFrequentItemSetGenerator generator = new ManualFrequentItemSetGenerator();
        generator.process(input);
    }

    public static ManualFrequentItemSetGeneratorInput getInput() {
        ManualFrequentItemSetGeneratorInput input = new ManualFrequentItemSetGeneratorInput();
        input.setFrameSize(FRAME_SIZE);
        input.setInputFilePath(FILE_INPUT_PATH);
        input.setMinSupport(MIN_SUPPORT);
        input.setWindowSize(WINDOW_SIZE);
        return input;
    }
}
