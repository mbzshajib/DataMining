package com.mbzshajib.utility.common.datasaver;

import com.mbzshajib.utility.callbacks.SaverOutput;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:25 AM
 * ****************************************************************
 */


public class DoubleDataSaverOutput extends SaverOutput {
    private String pathOfSavedDoubles;
    private String nameOfFile;
    private int numberOfDoubleSaved;

    public DoubleDataSaverOutput(String pathOfSavedDoubles, String nameOfFile, int numberOfDoubleSaved) {
        this.pathOfSavedDoubles = pathOfSavedDoubles;
        this.nameOfFile = nameOfFile;
        this.numberOfDoubleSaved = numberOfDoubleSaved;
    }

    public String getPathOfSavedDoubles() {
        return pathOfSavedDoubles;
    }

    public void setPathOfSavedDoubles(String pathOfSavedDoubles) {
        this.pathOfSavedDoubles = pathOfSavedDoubles;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public int getNumberOfDoubleSaved() {
        return numberOfDoubleSaved;
    }

    public void setNumberOfDoubleSaved(int numberOfDoubleSaved) {
        this.numberOfDoubleSaved = numberOfDoubleSaved;
    }
}
