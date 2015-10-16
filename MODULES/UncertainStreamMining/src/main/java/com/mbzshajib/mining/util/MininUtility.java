package com.mbzshajib.mining.util;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.UInputData;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/15/2015
 * @time: 11:42 PM
 * ****************************************************************
 */

public class MininUtility {
    public static List<FrequentItem> getFalsePositiveItems(List<List<UInputData>> transactionList, List<FrequentItem> frequentItemList, double minSuppport) {
        List<FrequentItem> falsePositiveList = new ArrayList<FrequentItem>();
        for (FrequentItem frequentItem : frequentItemList) {
            double probability = getFrequentItemProbability(transactionList, frequentItem);
            if (probability < minSuppport) {
                falsePositiveList.add(frequentItem);
            }

        }

        return falsePositiveList;
    }

    private static double getFrequentItemProbability(List<List<UInputData>> transactionList, FrequentItem frequentItem) {
        double support = 0;
        for (List<UInputData> transaction : transactionList) {
            support = support + getItemSupportForTransaction(transaction, frequentItem);
        }
        return support;
    }

    private static double getItemSupportForTransaction(List<UInputData> transaction, FrequentItem frequentItem) {
        double[] itemResult = new double[frequentItem.getFrequentItemSet().length];
        for (int i = 0; i < itemResult.length; i++) {
            itemResult[i] = getItemSupportForOne(transaction, frequentItem.getFrequentItemSet()[i]);
        }
        double result = 1;
        for (double prob : itemResult) {
            result = result * prob;
        }
        return result;
    }

    private static double getItemSupportForOne(List<UInputData> transaction, String s) {
        for (UInputData inputData : transaction) {
            if (inputData.getId().equalsIgnoreCase(s)) {
                return inputData.getItemPValue();
            }
        }
        return 0;
    }
}
