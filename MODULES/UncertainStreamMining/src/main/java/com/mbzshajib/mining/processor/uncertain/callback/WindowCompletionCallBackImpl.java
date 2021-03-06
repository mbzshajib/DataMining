package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.mining.NewStreamMinner;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineOutput;
import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.mining.processor.uncertain.simulator.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.simulator.MiningInput;
import com.mbzshajib.mining.processor.uncertain.simulator.USDMiningOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.mining.util.FrequentItemChecker;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.fpatterns.FNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 6:14 PM
 * ****************************************************************
 */


public class WindowCompletionCallBackImpl implements WindowCompletionCallback {
    private int windowNumber;
    private MiningInput miningInput;
    private List<MetaData> metaDataList;
    private TimeModel fPatternFinalizingTime;
    private int totalNodesInTree;

    public WindowCompletionCallBackImpl(MiningInput miningInput) {
        this.windowNumber = 0;
        this.miningInput = miningInput;
        this.metaDataList = new ArrayList<MetaData>();
    }

    @Override
    public void sendUpdate(TreeConstructionOutput treeConstructionOutput) throws ProcessingError {
        windowNumber++;
        UncertainStreamMineInput uncertainStreamMineInput = getMiningInput(treeConstructionOutput);
        totalNodesInTree =treeConstructionOutput.getUncertainTree().getRootNode().countAllChild();
//        UncertainStreamMiner uncertainStreamMiner = new UncertainStreamMiner();
//        UncertainStreamMineOutput miningResult = uncertainStreamMiner.process(uncertainStreamMineInput);
        NewStreamMinner streamMinner = new NewStreamMinner();
        UncertainStreamMineOutput miningResult = streamMinner.process(uncertainStreamMineInput);
        String path = miningInput.getMetaDataPath();
        String fileName = makeFileName();
//        System.out.println("mining completed.");
        USDMiningOutput uSDMiningOutput = getUsdMiningOutput(treeConstructionOutput, miningResult, path, fileName);
//
        try {
            writeOutputToFile(uSDMiningOutput, path, fileName);
            updateMetaData(path, fileName);
        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        printMessage(uSDMiningOutput);
    }

    private void printMessage(USDMiningOutput uSDMiningOutput) {
        System.out.println(uSDMiningOutput.toString());
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

    private USDMiningOutput getUsdMiningOutput(TreeConstructionOutput treeConstructionOutput, UncertainStreamMineOutput miningResult, String path, String fileName) {
        USDMiningOutput uSDMiningOutput = new USDMiningOutput(new File(path + fileName));
        uSDMiningOutput.setWindowNo(windowNumber);
        uSDMiningOutput.setFrameSize(miningInput.getFrameSize());
        uSDMiningOutput.setWindowSize(miningInput.getWindowSize());
        uSDMiningOutput.setMiningTime(miningResult.getMiningTime());
        uSDMiningOutput.setMinSupport(miningInput.getMinSupport());
        uSDMiningOutput.setDataSetFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());
        uSDMiningOutput.setTotalTreeNode(totalNodesInTree);


        List<FrequentItem> frequentItemListFinal = new ArrayList<FrequentItem>();
        List<FrequentItem> inFrequentList = new ArrayList<FrequentItem>();


        FNode fNode = miningResult.getFrequentNodesRoot();
//        List<FrequentItem> fList = miningResult.getFrequentItemList();
//        for (FrequentItem frequentItem : fList) {
//            String[] frequentItemSet = frequentItem.getFrequentItemSet();
//            Arrays.sort(frequentItemSet);
//            fNode.addChildesChain(frequentItemSet);
//        }
        List<String[]> allFrequentItems = fNode.getAllFrequentItems();
        long startTime = System.currentTimeMillis();
        for (String[] item : allFrequentItems) {
            boolean isFrequent = FrequentItemChecker.findIfItemIsFrequent(treeConstructionOutput.getWindowTransactionList(), item, miningInput.getMinSupport());
            FrequentItem frequentItem = new FrequentItem(item);
            if (isFrequent) {
                frequentItemListFinal.add(frequentItem);
            } else {
                inFrequentList.add(frequentItem);
            }
        }
        long endTime = System.currentTimeMillis();

        uSDMiningOutput.setFindinInfrequentItemTime(new TimeModel(startTime, endTime));
        uSDMiningOutput.setFrequentItemSize(frequentItemListFinal.size());
        uSDMiningOutput.setFrequentItemFound(frequentItemListFinal);
        uSDMiningOutput.setInFrequentItemSize(inFrequentList.size());
        uSDMiningOutput.setInFrequentItemFound(inFrequentList);

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

    private void printMessage(TreeConstructionOutput treeConstructionOutput, UncertainStreamMineOutput miningOutput) {
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
//                .append(miningOutput.getFrequentItemList().size())
                .append(Constants.TABBED_HASH)
                .append("Items are -> ");
//                .append(miningOutput.getFrequentItemList().toString());
        Logger.log("Window " + windowNumber, builder.toString());
    }

    private UncertainStreamMineInput getMiningInput(TreeConstructionOutput treeConstructionOutput) {
        UncertainStreamMineInput result = new UncertainStreamMineInput();
        result.setMinSupport(miningInput.getMinSupport());
        result.setUncertainTree(treeConstructionOutput.getUncertainTree());
        return result;
    }

}
