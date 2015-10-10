package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.MiningInput;
import com.mbzshajib.mining.processor.uncertain.USDMiningOutput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineInput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMineOutput;
import com.mbzshajib.mining.processor.uncertain.mining.UncertainStreamMiner;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;

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

    public WindowCompletionCallBackImpl(MiningInput miningInput) {
        this.windowNumber = 0;
        this.miningInput = miningInput;
        this.metaDataList = new ArrayList<MetaData>();
    }

    @Override
    public void sendUpdate(TreeConstructionOutput treeConstructionOutput) throws ProcessingError {
        windowNumber++;
        UncertainStreamMineInput uncertainStreamMineInput = getMiningInput(treeConstructionOutput);
        UncertainStreamMiner uncertainStreamMiner = new UncertainStreamMiner();
        UncertainStreamMineOutput miningResult = uncertainStreamMiner.process(uncertainStreamMineInput);

        String path = Constants.DIR_TMP + com.mbzshajib.mining.util.Constants.DIR_USM;
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

    private USDMiningOutput getUsdMiningOutput(TreeConstructionOutput treeConstructionOutput, UncertainStreamMineOutput miningResult, String path, String fileName) {
        USDMiningOutput USDMiningOutput = new USDMiningOutput(new File(path + fileName));
        USDMiningOutput.setWindowNo(windowNumber);
        USDMiningOutput.setFrameSize(miningInput.getFrameSize());
        USDMiningOutput.setWindowSize(miningInput.getWindowSize());
        USDMiningOutput.setMiningTime(miningResult.getMiningTime());
        USDMiningOutput.setMinSupport(miningInput.getMinSupport());
        USDMiningOutput.setDataSetFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());

        USDMiningOutput.setFrequentItemSize(miningResult.getFrequentItemList().size());
        USDMiningOutput.setFrequentItemFound(miningResult.getFrequentItemList());

        USDMiningOutput.setMiningTime(miningResult.getMiningTime());
        USDMiningOutput.setTreeConstructionTime(treeConstructionOutput.getTreeConstructionTime());
        USDMiningOutput.setScanningTransactionTime(treeConstructionOutput.getScanningTransactionTime());
        return USDMiningOutput;
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
                .append(miningOutput.getFrequentItemList().size())
                .append(Constants.TABBED_HASH)
                .append("Items are -> ")
                .append(miningOutput.getFrequentItemList().toString());
        Logger.log("Window " + windowNumber, builder.toString());
    }

    private UncertainStreamMineInput getMiningInput(TreeConstructionOutput treeConstructionOutput) {
        UncertainStreamMineInput result = new UncertainStreamMineInput();
        result.setMinSupport(miningInput.getMinSupport());
        result.setUncertainTree(treeConstructionOutput.getUncertainTree());
        return result;
    }

}
