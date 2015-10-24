package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.callback.SufCompleteCallback;
import com.mbzshajib.utility.model.Input;

import java.io.BufferedReader;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/21/2015
 * @time: 7:49 PM
 * ****************************************************************
 */

public class SufTreeConstructionInput implements Input{
    private int windowSize;
    private int frameSize;
    private BufferedReader bufferedReader;
    private SufCompleteCallback sufCompleteCallback;

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

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public SufCompleteCallback getSufCompleteCallback() {
        return sufCompleteCallback;
    }

    public void setSufCompleteCallback(SufCompleteCallback sufCompleteCallback) {
        this.sufCompleteCallback = sufCompleteCallback;
    }

}
