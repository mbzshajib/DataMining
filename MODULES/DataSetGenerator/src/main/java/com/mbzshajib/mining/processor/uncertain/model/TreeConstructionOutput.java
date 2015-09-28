package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.utility.model.Output;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib 
 * date: 9/20/2015
 * time: 11:03 PM
 * ****************************************************************
 */    

public class TreeConstructionOutput implements Output {
    UNode rootNode;

    public UNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(UNode rootNode) {
        this.rootNode = rootNode;
    }
}
