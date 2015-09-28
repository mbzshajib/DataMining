package com.mbzshajib.mining.processor.tree.initial;

import com.mbzshajib.utility.model.Input;

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

public class TreeInput implements Input {
    private String inputFilePath;
    private int windowSize;
    private int frameSize;

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

    @Override
    public String toString() {
        return "TreeInput{" +
                "inputFilePath='" + inputFilePath + '\'' +
                ", windowSize=" + windowSize +
                ", frameSize=" + frameSize +
                '}';
    }
}