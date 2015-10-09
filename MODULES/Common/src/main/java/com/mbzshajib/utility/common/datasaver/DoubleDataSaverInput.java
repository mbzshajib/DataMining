package com.mbzshajib.utility.common.datasaver;

import com.mbzshajib.utility.callbacks.SaverInput;

import java.util.List;

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


public class DoubleDataSaverInput extends SaverInput {
    private String pathToBeSaved;
    private String fileName;
    private List<Double> doubles;

    public DoubleDataSaverInput(String pathToBeSaved, String fileName, List<Double> doubles) {
        this.pathToBeSaved = pathToBeSaved;
        this.fileName = fileName;
        this.doubles = doubles;
    }

    public String getPathToBeSaved() {
        return pathToBeSaved;
    }

    public void setPathToBeSaved(String pathToBeSaved) {
        this.pathToBeSaved = pathToBeSaved;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Double> doubles) {
        this.doubles = doubles;
    }
}
