package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.configloader.JsonFileConfigModel;
import com.mbzshajib.utility.model.Input;

import java.io.File;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:17 AM
 * ****************************************************************
 */


public class UDataSetGeneratorInput extends JsonFileConfigModel implements Input {
    private String uncertainityDelemeter;
    private String transactionDelemeter;
    private String fileNameUncertainity;
    private String fileNameCertData;
    private String fileNameUncertData;
    private String pathUncertainity;
    private String pathCertData;
    private String pathUncertData;

    public UDataSetGeneratorInput(File file) {
        super(file);
    }

    public String getUncertainityDelemeter() {
        return uncertainityDelemeter;
    }

    public void setUncertainityDelemeter(String uncertainityDelemeter) {
        this.uncertainityDelemeter = uncertainityDelemeter;
    }

    public String getTransactionDelemeter() {
        return transactionDelemeter;
    }

    public void setTransactionDelemeter(String transactionDelemeter) {
        this.transactionDelemeter = transactionDelemeter;
    }

    public String getFileNameUncertainity() {
        return fileNameUncertainity;
    }

    public void setFileNameUncertainity(String fileNameUncertainity) {
        this.fileNameUncertainity = fileNameUncertainity;
    }

    public String getFileNameCertData() {
        return fileNameCertData;
    }

    public void setFileNameCertData(String fileNameCertData) {
        this.fileNameCertData = fileNameCertData;
    }

    public String getFileNameUncertData() {
        return fileNameUncertData;
    }

    public void setFileNameUncertData(String fileNameUncertData) {
        this.fileNameUncertData = fileNameUncertData;
    }

    public String getPathUncertainity() {
        return pathUncertainity;
    }

    public void setPathUncertainity(String pathUncertainity) {
        this.pathUncertainity = pathUncertainity;
    }

    public String getPathCertData() {
        return pathCertData;
    }

    public void setPathCertData(String pathCertData) {
        this.pathCertData = pathCertData;
    }

    public String getPathUncertData() {
        return pathUncertData;
    }

    public void setPathUncertData(String pathUncertData) {
        this.pathUncertData = pathUncertData;
    }

    @Override
    public String toString() {
        return "UDataSetGeneratorInput{" +
                "uncertainityDelemeter='" + uncertainityDelemeter + '\'' +
                ", transactionDelemeter='" + transactionDelemeter + '\'' +
                ", fileNameUncertainity='" + fileNameUncertainity + '\'' +
                ", fileNameCertData='" + fileNameCertData + '\'' +
                ", fileNameUncertData='" + fileNameUncertData + '\'' +
                ", pathUncertainity='" + pathUncertainity + '\'' +
                ", pathCertData='" + pathCertData + '\'' +
                ", pathUncertData='" + pathUncertData + '\'' +
                '}';
    }
}
