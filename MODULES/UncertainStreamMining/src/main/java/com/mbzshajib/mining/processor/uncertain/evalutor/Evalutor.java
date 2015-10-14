package com.mbzshajib.mining.processor.uncertain.evalutor;

import com.mbzshajib.mining.processor.uncertain.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.USDMiningOutput;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/11/2015
 * @time: 3:15 AM
 * ****************************************************************
 */

public class Evalutor implements Processor<EvalutorInput, EvalutorOutput> {
    private long totalTreeGenerationTime;
    private long totalFileReadTime;
    private long totalMiningTime;
    private long totalFrequentItem;
    private Map<Integer, Integer> frequentItemCount;

    @Override
    public EvalutorOutput process(EvalutorInput evalutorInput) throws ProcessingError {
        ConfigurationLoader<MetaDataConfig> metaDataLoader = new ConfigurationLoader<MetaDataConfig>(MetaDataConfig.class);
        try {
            MetaDataConfig metaDataConfig = metaDataLoader.loadConfigDataFromJsonFile(evalutorInput.getMiningMetaDataPath(), evalutorInput.getMetaDataName());
            List<MetaData> metaDataList = metaDataConfig.getMetaDataList();
            frequentItemCount = new HashMap<Integer, Integer>();
            USDMiningOutput output = null;
            for (MetaData metaData : metaDataList) {
                ConfigurationLoader<USDMiningOutput> loader = new ConfigurationLoader<USDMiningOutput>(USDMiningOutput.class);
                output = loader.loadConfigDataFromJsonFile(metaData.getFilePath(), metaData.getFileName());
                totalTreeGenerationTime += output.getTreeConstructionTime().getTimeNeeded();
                totalFileReadTime += output.getScanningTransactionTime().getTimeNeeded();
                totalMiningTime += output.getMiningTime().getTimeNeeded();
                totalFrequentItem += output.getFrequentItemSize();
                frequentItemCount.put(output.getWindowNo(), output.getFrequentItemSize());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MIN_SUP-> : ").append(output.getMinSupport()).append(Constants.TABBED_HASH)
                    .append("WinSize -> : ").append(output.getWindowSize()).append(Constants.TABBED_HASH)
                    .append("FrameSize-> : ").append(output.getFrameSize()).append(Constants.TABBED_HASH)
                    .append("Time-> : ").append(Constants.TAB)
                    .append("Tree: ").append(totalTreeGenerationTime).append(" ms ")
                    .append("Mining : ").append(totalMiningTime).append(" ms ")
                    .append("DB Read: ").append(totalFileReadTime).append(" ms ").append(Constants.TAB)
                    .append("TotalFrequentItem-> : ").append(Constants.TAB).append(totalFrequentItem).append(Constants.TAB)
                    .append("Total Window : ").append(Constants.TABBED_HASH).append(metaDataConfig.getMetaDataList().size());
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        return null;
    }
}
