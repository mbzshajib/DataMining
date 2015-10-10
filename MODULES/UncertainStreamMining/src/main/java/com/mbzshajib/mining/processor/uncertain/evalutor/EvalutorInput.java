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
    private String miningMetaDataPath;
    private String metaDataName;

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
