package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.SufMiner;
import com.mbzshajib.mining.processor.uncertain.SufMiningInput;
import com.mbzshajib.mining.processor.uncertain.SufMiningOutput;
import com.mbzshajib.mining.processor.uncertain.SufTreeConstructorOutput;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.simulator.MiningInput;
import com.mbzshajib.utility.model.ProcessingError;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/21/2015
 * @time: 7:44 PM
 * ****************************************************************
 */

public class SufWindowCompletionCallback implements SufCompleteCallback {
    private int windowNumber;
    private MiningInput miningInput;
    private List<MetaData> metaDataList;

    public SufWindowCompletionCallback(MiningInput miningInput) {
        this.windowNumber = 0;
        this.miningInput = miningInput;
        this.metaDataList = new ArrayList<MetaData>();
    }

    @Override
    public void sendUpdate(SufTreeConstructorOutput treeConstructionOutput) throws ProcessingError {
        treeConstructionOutput.toString();
        SufMiningInput sufMiningInput = new SufMiningInput();
        sufMiningInput.setMinSupport(miningInput.getMinSupport());
        sufMiningInput.setSufTree(treeConstructionOutput.getSufTree());
        SufMiner sufMiner = new SufMiner();
        SufMiningOutput process = sufMiner.process(sufMiningInput);
    }
}
