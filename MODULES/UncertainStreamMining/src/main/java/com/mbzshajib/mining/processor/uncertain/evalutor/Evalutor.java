package com.mbzshajib.mining.processor.uncertain.evalutor;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.MetaData;
import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.mining.processor.uncertain.simulator.MetaDataConfig;
import com.mbzshajib.mining.processor.uncertain.simulator.Result;
import com.mbzshajib.mining.processor.uncertain.simulator.USDMiningOutput;
import com.mbzshajib.mining.util.MininUtility;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private long totalInFrequentFindingTime;
    private int totalFrequentItem;
    private int totalFalsePositiveCount;
    private int totalTreeNodeCount;

    private long treeGenerationTime;
    private long fileReadTime;
    private long miningTime;
    private long inFrequentFindingTime;
    private int frequentItem;
    private int falsePositiveCount;
    private int treeNodeCount;

    private List<Result> resultList;
    List<List<UInputData>> transactionList = new ArrayList<List<UInputData>>();

    @Override
    public EvalutorOutput process(EvalutorInput evalutorInput) throws ProcessingError {
        ConfigurationLoader<MetaDataConfig> metaDataLoader = new ConfigurationLoader<MetaDataConfig>(MetaDataConfig.class);

        try {
            MetaDataConfig metaDataConfig = metaDataLoader.loadConfigDataFromJsonFile(evalutorInput.getMiningMetaDataPath(), evalutorInput.getMetaDataName());
            List<MetaData> metaDataList = metaDataConfig.getMetaDataList();
            USDMiningOutput output = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(evalutorInput.getMiningDataSetPath() + evalutorInput.getMiningDataSetFileName())));
            resultList = new ArrayList<Result>();
            boolean firstScan = true;
            for (MetaData metaData : metaDataList) {
                ConfigurationLoader<USDMiningOutput> loader = new ConfigurationLoader<USDMiningOutput>(USDMiningOutput.class);
                output = loader.loadConfigDataFromJsonFile(metaData.getFilePath(), metaData.getFileName());
//                if (evalutorInput.isFindFalseNegative()) {
//                    if (firstScan) {
//                        List<List<UInputData>> transactionList = getTransactionList(bufferedReader, output.getWindowSize() * output.getFrameSize());
//                        this.transactionList.addAll(transactionList);
//                        firstScan = false;
//                    } else {
//                        slideTransactionList(transactionList, output.getFrameSize());
//                        List<List<UInputData>> newTransactionList = getTransactionList(bufferedReader, output.getFrameSize());
//                        this.transactionList.addAll(newTransactionList);
//                    }
//                }
                fileReadTime = output.getScanningTransactionTime().getTimeNeeded();
                totalFileReadTime += output.getScanningTransactionTime().getTimeNeeded();

                treeGenerationTime = output.getTreeConstructionTime().getTimeNeeded();
                totalTreeGenerationTime += output.getTreeConstructionTime().getTimeNeeded();

                miningTime = output.getMiningTime().getTimeNeeded();
                totalMiningTime += output.getMiningTime().getTimeNeeded();
                inFrequentFindingTime = output.getFindinInfrequentItemTime().getTimeNeeded();
                totalInFrequentFindingTime += output.getFindinInfrequentItemTime().getTimeNeeded();
                frequentItem = output.getFrequentItemSize();
                totalFrequentItem += output.getFrequentItemSize();

                falsePositiveCount = output.getInFrequentItemSize();
                totalFalsePositiveCount += output.getInFrequentItemSize();


//                if (evalutorInput.isFindFalseNegative()) {
//                    falsePositiveCount = countFalsePositive(output.getFrequentItemFound(), output.getMinSupport());
//                    totalFalsePositiveCount += falsePositiveCount;
//                }
                treeNodeCount = output.getTotalTreeNode();
                totalTreeNodeCount += treeNodeCount;


                Result result = createResult(evalutorInput.getDataSetName(), output);
                resultList.add(result);

                writeResultForWindow(evalutorInput.getDataSetName(), evalutorInput.getResultFileName(), result);
            }
            writeResultOfEvalutor(true);
            bufferedReader.close();

        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        return null;
    }

    private void slideTransactionList(List<List<UInputData>> transactionList, int count) {
        for (int i = 0; i < count; i++) {
            List<UInputData> list = transactionList.get(0);
            transactionList.remove(list);
        }
    }

    private List<List<UInputData>> getTransactionList(BufferedReader bufferedReader, int count) throws IOException, DataNotFoundException {
        List<List<UInputData>> transactions = new ArrayList<List<UInputData>>();
        for (int i = 0; i < count; i++) {
            List<UInputData> transaction = getTransaction(bufferedReader);
            transactions.add(transaction);
        }
        return transactions;
    }

    private void writeResultOfEvalutor(boolean findFalsePositive) throws IOException {
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
                .append("TotalInFrequentFindingTime : ").append(totalInFrequentFindingTime).append(" MS ").append(Constants.TAB)

                .append("AvgFileReadTime : ").append(totalFileReadTime / resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgTreeConsTime: ").append(totalTreeGenerationTime / resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgMiningTime : ").append(totalMiningTime / resultList.size()).append(" MS ").append(Constants.TAB)
                .append("AvgInFrequentFindingTime : ").append(totalInFrequentFindingTime / resultList.size()).append(" MS ").append(Constants.TAB)

                .append("TotalTreeNode : ").append(totalTreeNodeCount).append(Constants.TAB)
                .append("AvgTreeNode : ").append(totalTreeNodeCount / resultList.size()).append(Constants.TAB)

                .append("TotalFrequentItems : ").append(totalFrequentItem).append(Constants.TAB)
                .append("AvgFrequentItems : ").append(totalFrequentItem / resultList.size()).append(Constants.TAB);
        if (findFalsePositive) {
            message
                    .append("TotalFalsePositive : ").append(totalFalsePositiveCount).append(Constants.TAB)
                    .append("AvgFalsePositive : ").append(totalFalsePositiveCount / resultList.size()).append(Constants.TAB);

        }
        message
                .append(Constants.NEW_LINE);
        FileUtility.writeSingleLine(path, fileName, message.toString());

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

        result.setTotalTreeNode(output.getTotalTreeNode());

        result.setFrameSize(output.getFrameSize());
        result.setWindowSize(output.getWindowSize());

        result.setTotalFileReadTime(fileReadTime);
        result.setTotalTreeGenerationTime(treeGenerationTime);
        result.setTotalMiningTime(miningTime);
        result.setTotalFalsePositiveFindingTime(inFrequentFindingTime);
        result.setFrequentItemSetCount(frequentItem);
        result.setFalsePositiveCount(output.getInFrequentItemSize());

        return result;
    }

    private List<FrequentItem> getFalsePositiveItems(List<FrequentItem> frequentItemFound, double minimumSupport) {
        //List<FrequentItem>=
        List<FrequentItem> falsePositive = MininUtility.getFalsePositiveItems(this.transactionList, frequentItemFound, minimumSupport);
        return falsePositive;

    }

    private List<UInputData> getTransaction(BufferedReader bufferedReader) throws IOException, DataNotFoundException {
        String line = bufferedReader.readLine();
        if (line == null) {
            bufferedReader.close();
            return Collections.emptyList();
        }
        List<UInputData> uNodeList = new ArrayList<UInputData>();
        String[] transactionItems = line.split(" ");
        for (int i = 0; i < transactionItems.length; i++) {
            String tmpId = transactionItems[i].split("-")[0];
            double tmpPValue = Double.parseDouble(transactionItems[i].split("-")[1]);
            UInputData data = new UInputData(tmpId, tmpPValue);
            uNodeList.add(data);
        }
        return uNodeList;
    }
}
