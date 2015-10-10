package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.mining.processor.uncertain.callback.ManualWindowCompletionCallback;
import com.mbzshajib.mining.processor.uncertain.callback.ManualWindowCompletionCallbackImpl;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    public static void main(String[] args) throws ProcessingError {
        ManualFrequentItemSetGeneratorInput input = null;
        try {
            input = getInput();
        } catch (FileNotFoundException e) {
            throw new ProcessingError(e);
        }
        ManualFrequentItemSetGenerator generator = new ManualFrequentItemSetGenerator();
        generator.process(input);
    }

    public static ManualFrequentItemSetGeneratorInput getInput() throws FileNotFoundException {
        ManualFrequentItemSetGeneratorInput input = new ManualFrequentItemSetGeneratorInput();
        ManualWindowCompletionCallback callback = new ManualWindowCompletionCallbackImpl();
        input.setFrameSize(FRAME_SIZE);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(FILE_INPUT_PATH)));
        input.setBufferedReader(bufferedReader);
        input.setMinSupport(MIN_SUPPORT);
        input.setWindowSize(WINDOW_SIZE);
        input.setCallback(callback);
        return input;
    }
}
