package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.utility.configloader.JsonFileConfigModel;

import java.io.File;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/11/2015
 * @time: 12:51 AM
 * ****************************************************************
 */

public class MetaDataConfig extends JsonFileConfigModel {
    private List<MetaData> metaDataList;

    public MetaDataConfig(File file) {
        super(file);
    }

    public List<MetaData> getMetaDataList() {
        return metaDataList;
    }

    public void setMetaDataList(List<MetaData> metaDataList) {
        this.metaDataList = metaDataList;
    }
}
