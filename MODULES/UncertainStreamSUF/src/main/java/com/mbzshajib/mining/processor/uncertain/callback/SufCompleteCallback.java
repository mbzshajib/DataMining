package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.tree.SufTreeConstructorOutput;
import com.mbzshajib.utility.model.ProcessingError;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/21/2015
 * @time: 7:46 PM
 * ****************************************************************
 */

public interface SufCompleteCallback {
    public void sendUpdate(SufTreeConstructorOutput treeConstructionOutput) throws ProcessingError;
}
