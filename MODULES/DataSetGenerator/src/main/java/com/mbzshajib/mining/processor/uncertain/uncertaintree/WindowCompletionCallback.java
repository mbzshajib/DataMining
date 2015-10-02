package com.mbzshajib.mining.processor.uncertain.uncertaintree;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/1/2015
 * @time: 1:14 PM
 * ****************************************************************
 */


public interface WindowCompletionCallback {
    void sendUpdate(TreeConstructionOutput treeConstructionOutput);
}
