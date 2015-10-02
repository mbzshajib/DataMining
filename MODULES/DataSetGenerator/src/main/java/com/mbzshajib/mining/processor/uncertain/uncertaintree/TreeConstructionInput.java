package com.mbzshajib.mining.processor.uncertain.uncertaintree;

import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:02 PM
 * ****************************************************************
 */

public class TreeConstructionInput implements Input {
    private String inputFilePath;
    private int windowSize;
    private int frameSize;
    private WindowCompletionCallback windowCompletionCallback;

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
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

    public WindowCompletionCallback getWindowCompletionCallback() {
        return windowCompletionCallback;
    }

    public void setWindowCompletionCallback(WindowCompletionCallback windowCompletionCallback) {
        this.windowCompletionCallback = windowCompletionCallback;
    }

    @Override
    public String toString() {
        return "TreeInput{" +
                "inputFilePath='" + inputFilePath + '\'' +
                ", windowSize=" + windowSize +
                ", frameSize=" + frameSize +
                '}';
    }
}
