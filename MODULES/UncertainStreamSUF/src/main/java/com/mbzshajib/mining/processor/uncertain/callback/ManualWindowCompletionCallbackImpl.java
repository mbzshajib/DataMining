package com.mbzshajib.mining.processor.uncertain.callback;

import com.mbzshajib.mining.processor.uncertain.simulator.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.simulator.ManualMiningOutput;
import com.mbzshajib.mining.processor.uncertain.simulator.MiningInput;
import com.mbzshajib.mining.processor.uncertain.maual.ManualFrequentItemSetGeneratorOutput;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
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
    private MiningInput miningInput;
    private int windowNumber;
    private List<MetaData> metaDataList;

    public ManualWindowCompletionCallbackImpl(MiningInput miningInput) {
        this.miningInput = miningInput;
        metaDataList = new ArrayList<MetaData>();
    }

    @Override
    public void sendUpdate(ManualFrequentItemSetGeneratorOutput output) throws ProcessingError {
        windowNumber++;
        String fileName = makeFileName();
        String dirName = com.mbzshajib.mining.util.Constants.DIR_META_MAN;

        ManualMiningOutput miningOutput = getManualMiningOutput(fileName, dirName, output);

        try {
            writeOutputToFile(fileName, dirName, miningOutput);
            updateMetaData(fileName, dirName);
        } catch (IOException e) {
            throw new ProcessingError(e);
        }


        printMessage(output);

    }

    private void writeOutputToFile(String fileName, String dirName, ManualMiningOutput miningOutput) throws IOException {
        ConfigurationLoader<ManualMiningOutput> loader = new ConfigurationLoader<ManualMiningOutput>(ManualMiningOutput.class);
        loader.generateJsonConfigFile(dirName, fileName, miningOutput);
    }

    private ManualMiningOutput getManualMiningOutput(String fileName, String dirName, ManualFrequentItemSetGeneratorOutput output) {
        ManualMiningOutput miningOutput = new ManualMiningOutput(new File(dirName + fileName));
        miningOutput.setFrameSize(miningInput.getFrameSize());
        miningOutput.setWindowSize(miningInput.getWindowSize());
        miningOutput.setMinSup(miningInput.getMinSupport());
        miningOutput.setWindowNo(windowNumber);
        miningOutput.setDataSetFilePath(miningInput.getDataSetPath() + miningInput.getDataSetName());
        miningOutput.setMiningTime(output.getMiningTime());
        miningOutput.setFrequentItemSize(output.getFrequentItemList().size());
        miningOutput.setFrequentItemFound(output.getFrequentItemList());

        return miningOutput;
    }

    private String makeFileName() {
        StringBuilder builder = new StringBuilder();
        builder.append("set")
                .append(Constants.UNDER_SCORE)
                .append(Constants.UNDER_SCORE)
                .append("frame_no")
                .append(Constants.UNDER_SCORE)
                .append(windowNumber)
                .append(Constants.FILE_EXT_JSON);
        return builder.toString();
    }

    private void printMessage(ManualFrequentItemSetGeneratorOutput output) {
        StringBuilder builder = new StringBuilder();
        builder.append("MAN_MINING completed for window => ")
                .append(windowNumber)
                .append(Constants.TABBED_HASH)
                .append("Total time passed=> ")
                .append(output.getMiningTime().getTimeNeeded())
                .append(Constants.TABBED_HASH)
                .append("DataSet=> ")
                .append(output.getFrequentItemList());

        Logger.log("Window " + windowNumber, builder.toString());
    }

    private void updateMetaData(String path, String fileName) throws IOException {
        String metaDataPath = miningInput.getMetaDataPath();
        String metaDataFileName = Constants.TMP + miningInput.getMetaDataFile();
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

}
