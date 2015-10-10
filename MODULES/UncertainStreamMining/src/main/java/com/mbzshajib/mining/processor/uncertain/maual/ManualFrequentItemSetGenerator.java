package com.mbzshajib.mining.processor.uncertain.maual;

import com.mbzshajib.mining.processor.uncertain.callback.ManualWindowCompletionCallback;
import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.utility.collection.PowerSetGenerator;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/5/2015
 * @time: 8:33 PM
 * ****************************************************************
 */

public class ManualFrequentItemSetGenerator implements Processor<ManualFrequentItemSetGeneratorInput, ManualFrequentItemSetGeneratorOutput>, Comparator<FrequentItem> {
    private BufferedReader bufferedReader;
    private ManualWindowCompletionCallback callback;
    private long startTime;

    @Override
    public ManualFrequentItemSetGeneratorOutput process(ManualFrequentItemSetGeneratorInput manualFrequentItemSetGeneratorInput) throws ProcessingError {
        startTime = System.currentTimeMillis();
        int frameSize = manualFrequentItemSetGeneratorInput.getFrameSize();
        int windowSize = manualFrequentItemSetGeneratorInput.getWindowSize();
        this.callback = manualFrequentItemSetGeneratorInput.getCallback();
        bufferedReader = manualFrequentItemSetGeneratorInput.getBufferedReader();
        double minSup = manualFrequentItemSetGeneratorInput.getMinSupport();
        int initialTransactionCount = windowSize * frameSize;
        List<FrequentItem> frequentItems = null;
        ManualFrequentItemSetGeneratorOutput output = null;
        try {
            List<List<UInputData>> transactionList = getTransactionList(initialTransactionCount);
            frequentItems = getFrequentItems(transactionList, minSup);

            if (initialTransactionCount != transactionList.size()) {
                return null;
            }
            callback.sendUpdate(createUpdate(frequentItems));

            List<List<UInputData>> newList;
            while (true) {
                newList = getTransactionList(frameSize);
                if (newList.size() != frameSize) {
                    break;
                }
                removeFromList(transactionList, frameSize);
                transactionList.addAll(newList);
                frequentItems = getFrequentItems(transactionList, minSup);
                System.out.println(frequentItems);
                callback.sendUpdate(createUpdate(frequentItems));
            }
//                newList = getTransactionList(frameSize);


        } catch (FileNotFoundException e) {
            throw new ProcessingError(e);
        } catch (IOException e) {
            throw new ProcessingError(e);
        }

        return createUpdate(frequentItems);
    }

    private void removeFromList(List<List<UInputData>> transactionList, int frameSize) {
        for (int i = 0; i < frameSize; i++) {
            List<UInputData> tmp = transactionList.get(i);
            transactionList.remove(tmp);
            i--;
            frameSize--;
        }
    }

    private ManualFrequentItemSetGeneratorOutput createUpdate(List<FrequentItem> frequentItems) {
        long endTime = System.currentTimeMillis();
        TimeModel timeModel = new TimeModel(startTime, endTime);
        startTime = endTime;
        ManualFrequentItemSetGeneratorOutput output = new ManualFrequentItemSetGeneratorOutput();
        output.setMiningTime(timeModel);
        output.setFrequentItemList(frequentItems);
        return output;
    }

    private List<FrequentItem> getFrequentItems(List<List<UInputData>> transactionList, double minSupport) {
        List<List<UInputData>> frequentList = new ArrayList<List<UInputData>>();
        List<UInputData> distinctInputList = getDistinctTransaction(transactionList);
        PowerSetGenerator<UInputData> powerSetGenerator = new PowerSetGenerator<UInputData>();
        List<List<UInputData>> powerSet = powerSetGenerator.generatePowerSet(distinctInputList);
        for (List<UInputData> itemToBeTested : powerSet) {
            if (!itemToBeTested.isEmpty()) {
                boolean isFrequent = findIfItemIsFrequent(transactionList, itemToBeTested, minSupport);
                if (isFrequent) {
                    frequentList.add(itemToBeTested);
                }
            }
        }
        List<FrequentItem> result = getFrequentItemList(frequentList);
        return result;
    }

    private List<FrequentItem> getFrequentItemList(List<List<UInputData>> frequentList) {
        List<FrequentItem> result = new ArrayList<FrequentItem>();
        for (List<UInputData> item : frequentList) {
            FrequentItem frequentItem = new FrequentItem();
            for (UInputData data : item) {
                frequentItem.addFrequentItem(data.getId());
            }
            result.add(frequentItem);
        }
        return result;
    }

    private boolean findIfItemIsFrequent(List<List<UInputData>> transactionList, List<UInputData> itemToBeTested, double minSupport) {
        double probability = 0;
        for (List<UInputData> transaction : transactionList) {
            probability = probability + getItemProbability(transaction, itemToBeTested);
        }
        if (probability < minSupport) {
            return false;
        } else {
            return true;
        }
    }

    private double getItemProbability(List<UInputData> transaction, List<UInputData> itemToBeTested) {
        double result = 0;
        List<Double> probList = new ArrayList<Double>();
        for (UInputData data : itemToBeTested) {
            double tmpValue = isTransactionContainsItem(transaction, data);
            if (tmpValue != 0) {
                probList.add(tmpValue);
            }
        }
        if (probList.size() == itemToBeTested.size()) {
            result = 1;
            for (Double val : probList) {
                result = result * val.doubleValue();
            }
        }
        return result;

    }

    private double isTransactionContainsItem(List<UInputData> transaction, UInputData itemToBeTested) {
        double result = 0;
        for (UInputData data : transaction) {
            if (data.getId().equals(itemToBeTested.getId())) {
                result = data.getItemPValue();
                break;
            }
        }
        return result;
    }

    private List<UInputData> getDistinctTransaction(List<List<UInputData>> transactionList) {
        List<UInputData> result = new ArrayList<UInputData>();
        for (int i = 0; i < transactionList.size(); i++) {
            List<UInputData> transaction = transactionList.get(i);
            for (int j = 0; j < transaction.size(); j++) {
                UInputData item = transaction.get(j);
                if (!isUInputDataContainsUData(result, item)) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    private boolean isUInputDataContainsUData(List<UInputData> list, UInputData item) {
        boolean found = false;
        for (UInputData inputData : list) {
            if (inputData.getId().equalsIgnoreCase(item.getId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private List<List<UInputData>> getTransactionList(int count) throws IOException {
        List<List<UInputData>> result = new ArrayList<List<UInputData>>();
        for (int i = 0; i < count; i++) {
            List<UInputData> transaction = new ArrayList<UInputData>();
            String line = bufferedReader.readLine();
            if(line==null){
                return result;
            }
            String[] transactionItems = line.split(" ");
            for (String item : transactionItems) {
                String[] val = item.split("-");
                UInputData data = new UInputData(val[0], Double.parseDouble(val[1]));
                transaction.add(data);
            }
            result.add(transaction);

        }
        return result;
    }

    @Override
    public int compare(FrequentItem o1, FrequentItem o2) {
        return 0;
    }

    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
}
