package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.mining.SufMiner;
import com.mbzshajib.mining.processor.uncertain.mining.SufMiningInput;
import com.mbzshajib.mining.processor.uncertain.mining.SufMiningOutput;
import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.simulator.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.simulator.MiningInput;
import com.mbzshajib.mining.processor.uncertain.simulator.USDMiningOutput;
import com.mbzshajib.mining.processor.uncertain.tree.SufTreeConstructorOutput;
import com.mbzshajib.mining.util.FrequentItemChecker;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        windowNumber++;
        SufMiningInput sufMiningInput = getSufMiningInput(treeConstructionOutput);
        SufMiner sufMiner = new SufMiner();
        SufMiningOutput miningResult = sufMiner.process(sufMiningInput);

        String path = miningInput.getMetaDataPath();
        String fileName = makeFileName();
        USDMiningOutput uSDMiningOutput = getUsdMiningOutput(treeConstructionOutput, miningResult, path, fileName);
        try {
            writeOutputToFile(uSDMiningOutput, path, fileName);
            updateMetaData(path, fileName);
        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        printMessage(treeConstructionOutput, miningResult);

    }

    private void writeOutputToFile(USDMiningOutput uSDMiningOutput, String path, String fileName) throws IOException {
        ConfigurationLoader<USDMiningOutput> loader = new ConfigurationLoader<USDMiningOutput>(USDMiningOutput.class);
        loader.generateJsonConfigFile(path, fileName, uSDMiningOutput);
    }

    private void updateMetaData(String path, String fileName) throws IOException {
        String metaDataPath = miningInput.getMetaDataPath();
        String metaDataFileName = miningInput.getMetaDataFile();
        MetaData metaData = new MetaData(path, fileName);
        if (metaDataList.size() == 0) {
            MetaDataConfig output = new MetaDataConfig(new File(metaDataPath + metaDataFileName));
            ConfigurationLoader<MetaDataConfig> tmp = new ConfigurationLoader<MetaDataConfig>(MetaDataConfig.class);
            tmp.generateJsonConfigFile(metaDataPath, metaDataFileName, output);
        }
        metaDataList.add(metaData);
        ConfigurationLoader<MetaDataConfig> loader = new ConfigurationLoader<MetaDataConfig>(MetaDataConfig.class);
        MetaDataConfig metaDataConfig = loader.loadConfigDataFromJsonFile(new File(metaDataPath + metaDataFileName));
        metaDataConfig.setMetaDataList(metaDataList);
        loader.generateJsonConfigFile(metaDataPath, metaDataFileName, metaDataConfig);
    }


    private USDMiningOutput getUsdMiningOutput(SufTreeConstructorOutput treeConstructionOutput, SufMiningOutput miningResult, String path, String fileName) {
        USDMiningOutput uSDMiningOutput = new USDMiningOutput(new File(path + fileName));
        uSDMiningOutput.setWindowNo(windowNumber);
        uSDMiningOutput.setFrameSize(miningInput.getFrameSize());
        uSDMiningOutput.setWindowSize(miningInput.getWindowSize());
        uSDMiningOutput.setMiningTime(miningResult.getMiningTime());
        uSDMiningOutput.setMinSupport(miningInput.getMinSupport());
        uSDMiningOutput.setDataSetFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());
        uSDMiningOutput.setTotalTreeNode(treeConstructionOutput.getSufTree().getRootNode().countAllChild());
        List<FrequentItem> fList = miningResult.getFrequentItemList();

        for (FrequentItem item : fList) {
            boolean ifItemIsFrequent = FrequentItemChecker.findIfItemIsFrequent(treeConstructionOutput.getWindowTransactionList(), item.getFrequentItemSet(), miningInput.getMinSupport());
            if (!ifItemIsFrequent) {
                System.out.println("Not Frequent Found" + Arrays.toString(item.getFrequentItemSet()));
                throw new IllegalArgumentException("This is exac mining algorithm and must contains not no infrequent items.");
            }
        }

        uSDMiningOutput.setFrequentItemSize(fList.size());
        uSDMiningOutput.setFrequentItemFound(fList);

        uSDMiningOutput.setMiningTime(miningResult.getMiningTime());
        uSDMiningOutput.setTreeConstructionTime(treeConstructionOutput.getTreeConstructionTime());
        uSDMiningOutput.setScanningTransactionTime(treeConstructionOutput.getScanningTransactionTime());
        return uSDMiningOutput;
    }

    private String makeFileName() {
        StringBuilder builder = new StringBuilder();
        builder.append("set")
                .append(Constants.UNDER_SCORE)
                .append("frame_no")
                .append(Constants.UNDER_SCORE)
                .append(windowNumber)
                .append(Constants.FILE_EXT_JSON);
        return builder.toString();
    }

    private SufMiningInput getSufMiningInput(SufTreeConstructorOutput treeConstructionOutput) {
        SufMiningInput sufMiningInput = new SufMiningInput();
        sufMiningInput.setMinSupport(miningInput.getMinSupport());
        sufMiningInput.setSufTree(treeConstructionOutput.getSufTree());
        return sufMiningInput;
    }

    private void printMessage(SufTreeConstructorOutput treeConstructionOutput, SufMiningOutput miningOutput) {
        StringBuilder builder = new StringBuilder();
        builder.append("USM_MINING completed for window => ")
                .append(windowNumber)
                .append(Constants.TABBED_HASH)
                .append("Tree construction time-> ")
                .append(treeConstructionOutput.getTreeConstructionTime().getTimeNeeded() - treeConstructionOutput.getScanningTransactionTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("DB scanning time-> ")
                .append(treeConstructionOutput.getScanningTransactionTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("Total mining time-> ")
                .append(miningOutput.getMiningTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("minimum support ")
                .append(miningInput.getMinSupport())
                .append(Constants.TABBED_HASH)
                .append("Found Frequent ItemSets Count ->")
                .append(miningOutput.getFrequentItemList().size())
                .append(Constants.TABBED_HASH)
                .append("Items are -> ")
                .append(miningOutput.getFrequentItemList().toString());
        Logger.log("Window " + windowNumber, builder.toString());
    }
}
