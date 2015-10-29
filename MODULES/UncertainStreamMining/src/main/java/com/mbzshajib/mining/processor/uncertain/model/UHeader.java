package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.utility.common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/29/2015
 * @time: 8:22 PM
 * ****************************************************************
 */

public class UHeader {
    List<UHItem> itemList;

    public UHeader() {
        this.itemList = new ArrayList<UHItem>();
    }

    public void addNodeToHeader(UNode node) {
        UHItem headerItem = getHeaderItem(node.getId());
        if (headerItem == null) {
            headerItem = new UHItem(node.getId());
            headerItem.setInitialValue(node.getTotalSupport(), node.getTotalPrefix(), node.getMiningProbability());
            itemList.add(headerItem);
        } else {
            headerItem.updateValues(node.getTotalSupport(), node.getTotalPrefix(), node.getMiningProbability());
        }


    }

    public UHItem getHeaderItem(String id) {
        for (UHItem item : itemList) {
            if (item.getItemId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void sortByPrefixDsc() {
        Collections.sort(itemList, new Comparator<UHItem>() {
            @Override
            public int compare(UHItem itemOne, UHItem itemTwo) {
                if (itemOne.getTotalPrefix() == itemTwo.getTotalPrefix()) {
                    return 0;
                } else if (itemOne.getTotalPrefix() > itemTwo.getTotalPrefix()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });


    }

    public void sortBySupportDsc() {
        Collections.sort(itemList, new Comparator<UHItem>() {
            @Override
            public int compare(UHItem itemOne, UHItem itemTwo) {
                if (itemOne.getTotalSupport() == itemTwo.getTotalSupport()) {
                    return 0;
                } else if (itemOne.getTotalSupport() > itemTwo.getTotalSupport()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });


    }

    public void sortByMiningValueDsc() {
        Collections.sort(itemList, new Comparator<UHItem>() {
            @Override
            public int compare(UHItem itemOne, UHItem itemTwo) {
                if (itemOne.getTotalMiningVal() == itemTwo.getTotalMiningVal()) {
                    return 0;
                } else if (itemOne.getTotalMiningVal() > itemTwo.getTotalMiningVal()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        for (UHItem item : itemList) {
            stringBuilder.append(item.traverse());
            stringBuilder.append(Constants.NEW_LINE);
        }

        return stringBuilder.toString();
    }
}