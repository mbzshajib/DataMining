package com.mbzshajib.mining.util;

import com.mbzshajib.mining.processor.uncertain.model.SufInputData;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/25/2015
 * @time: 2:42 AM
 * ****************************************************************
 */

public class FrequentItemChecker {
    public static boolean findIfItemIsFrequent(List<List<SufInputData>> transactionList, String[] itemToBeTested, double minSupport) {
        double probability = 0;
        for (List<SufInputData> transaction : transactionList) {
            double prev = getItemProbability(transaction, itemToBeTested);
            probability = probability + prev;
        }
        if (probability < minSupport) {
            return false;
        } else {
            return true;
        }
    }
    private static double getItemProbability(List<SufInputData> transaction, String[] itemToBeTested) {
        double result = 0;
        List<Double> probList = new ArrayList<Double>();
        for (String data : itemToBeTested) {
            double tmpValue = getItemSupportInTransaction(transaction, data);
            if (tmpValue != 0) {
                probList.add(tmpValue);
            }
        }
        if (probList.size() == itemToBeTested.length) {
            result = 1;
            for (Double val : probList) {
                result = result * val.doubleValue();
            }
        }
        return result;

    }
    private static double getItemSupportInTransaction(List<SufInputData> transaction, String itemToBeTested) {
        double result = 0;
        for (SufInputData data : transaction) {
            if (data.getId().equals(itemToBeTested)) {
                result = data.getProbability();
                break;
            }
        }
        return result;
    }
}
