package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.callback.WindowCompletionCallback;
import com.mbzshajib.utility.model.Input;

import java.io.BufferedReader;

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
    private int windowSize;
    private int frameSize;
    private BufferedReader bufferedReader;
    private WindowCompletionCallback windowCompletionCallback;

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

    public WindowCompletionCallback getWindowCompletionCallback() {
        return windowCompletionCallback;
    }

    public void setWindowCompletionCallback(WindowCompletionCallback windowCompletionCallback) {
        this.windowCompletionCallback = windowCompletionCallback;
    }


}
