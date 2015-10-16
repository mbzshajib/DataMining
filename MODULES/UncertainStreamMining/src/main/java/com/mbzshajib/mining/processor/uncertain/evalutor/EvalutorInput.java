package com.mbzshajib.mining.processor.uncertain.evalutor;

import com.mbzshajib.utility.model.Input;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/11/2015
 * @time: 3:15 AM
 * ****************************************************************
 */

public class EvalutorInput implements Input {
    private String resultFileName;
    private String dataSetName;
    private String miningDataSetPath;
    private String miningDataSetFileName;
    private String miningMetaDataPath;
    private String metaDataName;

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getMiningDataSetPath() {
        return miningDataSetPath;
    }

    public void setMiningDataSetPath(String miningDataSetPath) {
        this.miningDataSetPath = miningDataSetPath;
    }

    public String getMiningDataSetFileName() {
        return miningDataSetFileName;
    }

    public void setMiningDataSetFileName(String miningDataSetFileName) {
        this.miningDataSetFileName = miningDataSetFileName;
    }

    public String getMiningMetaDataPath() {
        return miningMetaDataPath;
    }

    public String getMetaDataName() {
        return metaDataName;
    }

    public void setMiningMetaDataPath(String miningMetaDataPath) {
        this.miningMetaDataPath = miningMetaDataPath;
    }

    public void setMetaDataName(String metaDataName) {
        this.metaDataName = metaDataName;
    }
}
