package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.mining.processor.uncertain.callback.ManualWindowCompletionCallback;
import com.mbzshajib.utility.model.Input;

import java.io.BufferedReader;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/5/2015
 * @time: 8:33 PM
 * ****************************************************************
 */

public class ManualFrequentItemSetGeneratorInput implements Input {
    private BufferedReader bufferedReader;
    private int windowSize;
    private int frameSize;
    private double minSupport;
    private ManualWindowCompletionCallback callback;

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }

    public ManualWindowCompletionCallback getCallback() {
        return callback;
    }

    public void setCallback(ManualWindowCompletionCallback callback) {
        this.callback = callback;
    }
}
