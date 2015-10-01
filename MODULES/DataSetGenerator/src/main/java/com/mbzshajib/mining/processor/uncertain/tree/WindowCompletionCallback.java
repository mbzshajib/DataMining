package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionOutput;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 10/1/2015
 * time: 1:14 PM
 * ****************************************************************
 */


public interface WindowCompletionCallback {
    void sendUpdate(TreeConstructionOutput treeConstructionOutput);
}
