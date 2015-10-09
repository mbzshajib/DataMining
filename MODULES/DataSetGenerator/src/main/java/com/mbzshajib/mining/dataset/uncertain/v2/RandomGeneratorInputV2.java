package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.callbacks.DataSaver;
import com.mbzshajib.utility.configloader.JsonFileConfigModel;
import com.mbzshajib.utility.model.Input;

import java.io.File;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:41 PM
 * ****************************************************************
 */


public class RandomGeneratorInputV2 extends JsonFileConfigModel implements Input {
    private int numberOfRandomToBeGenerated;
    private List<RInputInfo> list;
    private DataSaver dataSaver;
    private String outputFileName;
    private String outputPath;

    public int getNumberOfRandomToBeGenerated() {
        return numberOfRandomToBeGenerated;
    }

    public void setNumberOfRandomToBeGenerated(int numberOfRandomToBeGenerated) {
        this.numberOfRandomToBeGenerated = numberOfRandomToBeGenerated;
    }

    public RandomGeneratorInputV2(File file) {
        super(file);
    }

    public List<RInputInfo> getList() {
        return list;
    }

    public void setList(List<RInputInfo> list) {
        this.list = list;
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }

    public void setDataSaver(DataSaver dataSaver) {
        this.dataSaver = dataSaver;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public String toString() {
        return "RandomGeneratorInputV2{" +
                "numberOfRandomToBeGenerated=" + numberOfRandomToBeGenerated +
                ", list=" + list +
                ", dataSaver=" + dataSaver +
                ", outputFileName='" + outputFileName + '\'' +
                ", outputPath='" + outputPath + '\'' +
                '}';
    }
}
