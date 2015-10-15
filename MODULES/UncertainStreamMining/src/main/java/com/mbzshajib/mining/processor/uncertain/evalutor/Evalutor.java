package com.mbzshajib.mining.processor.uncertain.evalutor;

import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.simulator.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.simulator.Result;
import com.mbzshajib.mining.processor.uncertain.simulator.USDMiningOutput;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private int totalFrequentItem;
    private int totalFalsePositiveCount;

    private long treeGenerationTime;
    private long fileReadTime;
    private long miningTime;
    private int frequentItem;
    private int falsePositiveCount;

    private List<Result> resultList;

    @Override
    public EvalutorOutput process(EvalutorInput evalutorInput) throws ProcessingError {
        ConfigurationLoader<MetaDataConfig> metaDataLoader = new ConfigurationLoader<MetaDataConfig>(MetaDataConfig.class);
        try {
            MetaDataConfig metaDataConfig = metaDataLoader.loadConfigDataFromJsonFile(evalutorInput.getMiningMetaDataPath(), evalutorInput.getMetaDataName());
            List<MetaData> metaDataList = metaDataConfig.getMetaDataList();
            USDMiningOutput output = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(evalutorInput.getMiningDataSetPath() + evalutorInput.getMiningDataSetFileName())));
            resultList = new ArrayList<Result>();

            for (MetaData metaData : metaDataList) {
                ConfigurationLoader<USDMiningOutput> loader = new ConfigurationLoader<USDMiningOutput>(USDMiningOutput.class);
                output = loader.loadConfigDataFromJsonFile(metaData.getFilePath(), metaData.getFileName());
                totalTreeGenerationTime += output.getTreeConstructionTime().getTimeNeeded();
                totalFileReadTime += output.getScanningTransactionTime().getTimeNeeded();
                totalMiningTime += output.getMiningTime().getTimeNeeded();
                totalFrequentItem += output.getFrequentItemSize();
                totalFalsePositiveCount += countFalsePositive(bufferedReader, output.getWindowSize(), output.getFrameSize());

                treeGenerationTime = output.getTreeConstructionTime().getTimeNeeded();
                fileReadTime = output.getScanningTransactionTime().getTimeNeeded();
                miningTime = output.getMiningTime().getTimeNeeded();
                frequentItem = output.getFrequentItemSize();
                falsePositiveCount = countFalsePositive(bufferedReader, output.getWindowSize(), output.getFrameSize());

                Result result = createResult(evalutorInput.getDataSetName(), output);
                resultList.add(result);

                writeResultForWindow(evalutorInput.getDataSetName(), evalutorInput.getResultFileName(), result);
            }
            writeResultOfEvalutor();
            bufferedReader.close();

        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        return null;
    }

    private void writeResultOfEvalutor() throws IOException {
        String path = "Result/";
        String fileName = "result.result";
        StringBuilder message = new StringBuilder();
        message
                .append("Data Set Name : ").append(resultList.get(0).getDataSetName()).append(Constants.TAB)
                .append("Data Set Size : ").append(resultList.get(0).getTotalTransactionInTree()).append(Constants.TAB)
                .append("MinSup : ").append(resultList.get(0).getMinSupport()).append(Constants.TAB)

                .append("Window : ").append(resultList.get(0).getWindowSize()).append(Constants.TAB)
                .append("Frame : ").append(resultList.get(0).getFrameSize()).append(Constants.TAB)

                .append("TotalFileReadTime : ").append(totalFileReadTime).append(" MS ").append(Constants.TAB)
                .append("TotalTreeConsTime: ").append(totalTreeGenerationTime).append(" MS ").append(Constants.TAB)
                .append("TotalMiningTime : ").append(totalMiningTime).append(" MS ").append(Constants.TAB)

                .append("AvgFileReadTime : ").append(totalFileReadTime/resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgTreeConsTime: ").append(totalTreeGenerationTime/resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgMiningTime : ").append(totalMiningTime/resultList.size()).append(" MS ").append(Constants.TAB)

                .append("TotalFrequentItems : ").append(totalFrequentItem).append(" MS ").append(Constants.TAB)
                .append("TotalFalsePositive : ").append(totalFalsePositiveCount).append(" MS ").append(Constants.TAB)

                .append("AvgFrequentItems : ").append(totalFrequentItem/resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgFalsePositive : ").append(totalFalsePositiveCount/resultList.size()).append(" MS ").append(Constants.TAB)
                .append(Constants.NEW_LINE);
        FileUtility.writeSingleLine(path,fileName,message.toString());

    }

    private void writeResultForWindow(String dataSetName, String resultFileName, Result result) throws IOException {
        String resultDir = "Result/" + dataSetName + "/";
        double minSupport = result.getMinSupport();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        minSupport = Double.valueOf(decimalFormat.format(minSupport));
        FileUtility.writeSingleLine(resultDir, resultFileName, result.toResult());
    }

    private Result createResult(String dataSetName, USDMiningOutput output) {
        Result result = new Result();
        result.setDataSetName(dataSetName);
        result.setMinSupport(output.getMinSupport());
        result.setTotalTransactionInTree(output.getFrameSize() * output.getWindowSize());

        result.setFrameSize(output.getFrameSize());
        result.setWindowSize(output.getWindowSize());

        result.setTotalFileReadTime(fileReadTime);
        result.setTotalTreeGenerationTime(treeGenerationTime);
        result.setTotalMiningTime(miningTime);

        result.setFrequentItemSetCount(frequentItem);
        result.setFalsePositiveCount(falsePositiveCount);

        return result;
    }

    private int countFalsePositive(BufferedReader bufferedReader, int windowSize, int frameSize) {
        return 0;
    }
}
