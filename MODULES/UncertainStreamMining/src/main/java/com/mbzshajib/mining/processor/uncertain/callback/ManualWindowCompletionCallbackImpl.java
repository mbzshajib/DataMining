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
 * @time: 10:56 PM
 * ****************************************************************
 */

public class ManualWindowCompletionCallbackImpl implements ManualWindowCompletionCallback {
    @Override
    public void sendUpdate(ManualFrequentItemSetGeneratorOutput output) {
        System.out.println(output.toString());
    }
}
