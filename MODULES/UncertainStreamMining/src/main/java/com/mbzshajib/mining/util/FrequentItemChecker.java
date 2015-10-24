package com.mbzshajib.mining.util;

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
 * @date: 10/25/2015
 * @time: 2:42 AM
 * ****************************************************************
 */

public class FrequentItemChecker {
    public static boolean findIfItemIsFrequent(List<List<UInputData>> transactionList, String[] itemToBeTested, double minSupport) {
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
    private static double getItemProbability(List<UInputData> transaction, String[] itemToBeTested) {
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
    private static double getItemSupportInTransaction(List<UInputData> transaction, String itemToBeTested) {
        double result = 0;
        for (UInputData data : transaction) {
            if (data.getId().equals(itemToBeTested)) {
                result = data.getItemPValue();
                break;
            }
        }
        return result;
    }
}
