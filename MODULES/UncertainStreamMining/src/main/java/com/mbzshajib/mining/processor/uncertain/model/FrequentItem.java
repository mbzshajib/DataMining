package com.mbzshajib.mining.processor.uncertain.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> frequentSet;


    public FrequentItem() {
        frequentSet = new ArrayList<String>();
    }

    public FrequentItem(FrequentItem frequentItem) {
        this.frequentSet = new ArrayList<String>();
        for (String string : frequentItem.getFrequentSet()) {
            this.frequentSet.add(new String(string));
        }
    }

    public void addFrequentItem(String itemId) {
        boolean found = false;
        for (String id : frequentSet) {
            if (id.equals(itemId)) {
                found = true;
                break;
            }


        }
        if (!found) {
            frequentSet.add(new String(itemId));
        }
    }

    public List<String> getFrequentSet() {
        return frequentSet;
    }

    public String traverse() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item: ");
        for (String string : frequentSet) {
            builder.append("(")
                    .append(string)
                    .append(")");
        }
        return builder.toString();
    }
}
