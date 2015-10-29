package com.mbzshajib.mining.processor.uncertain.model;

import java.util.Arrays;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/4/2015
 * @time: 2:16 AM
 * ****************************************************************
 */

public class FrequentItem {
    private String[] frequentItemSet;


    public FrequentItem() {
        frequentItemSet = new String[]{};
    }

    public FrequentItem(String id) {
        frequentItemSet = new String[]{id};

    }

    public FrequentItem(FrequentItem frequentItem) {
        this.frequentItemSet = new String[frequentItem.getFrequentItemSet().length];
        for (int i = 0; i < frequentItem.getFrequentItemSet().length; i++) {
            this.frequentItemSet[i] = new String(frequentItem.getFrequentItemSet()[i]);

        }
    }

    public FrequentItem(String[] item) {
        this.frequentItemSet = item;
    }

    public void addFrequentItem(String itemId) {
        boolean found = false;
        for (String id : frequentItemSet) {
            if (id.equals(itemId)) {
                found = true;
                break;
            }


        }
        if (!found) {
            String[] tmp = new String[this.frequentItemSet.length + 1];
            for (int i = 0; i < frequentItemSet.length; i++) {
                tmp[i] = frequentItemSet[i];
            }
            tmp[this.frequentItemSet.length] = new String(itemId);
            frequentItemSet = tmp;
        }
    }

    public String[] getFrequentItemSet() {
        return frequentItemSet;
    }

    public String traverse() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item: ");
        for (String string : frequentItemSet) {
            builder.append("(")
                    .append(string)
                    .append(")");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "FI{" +
                Arrays.toString(frequentItemSet) +
                '}';
    }
}
