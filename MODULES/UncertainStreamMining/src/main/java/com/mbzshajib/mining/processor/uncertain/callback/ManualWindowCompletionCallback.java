package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.maual.ManualFrequentItemSetGeneratorOutput;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/10/2015
 * @time: 10:34 PM
 * ****************************************************************
 */

public interface ManualWindowCompletionCallback {
    void sendUpdate(ManualFrequentItemSetGeneratorOutput output);
}
