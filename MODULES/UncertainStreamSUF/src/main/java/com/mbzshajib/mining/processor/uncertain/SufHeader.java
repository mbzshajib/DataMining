package com.mbzshajib.mining.processor.uncertain;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/20/2015
 * @time: 11:30 PM
 * ****************************************************************
 */

public class SufHeader {
    private List<SufHItem> itemList;

    public SufHeader() {
        itemList = new ArrayList<SufHItem>();
    }

    public List<SufHItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SufHItem> itemList) {
        this.itemList = itemList;
    }

    public void addNewNodeToHeader(SufNode node) {
        SufHItem item = getHItem(node.getId());
        if (item == null) {
            item = new SufHItem(node.getId());
            item.getNodeList().add(node);
            itemList.add(item);
            item.setTotalSupport(node.getProbability());
        } else {
            item.getNodeList().add(node);
            item.updateSupport(node.getProbability());
        }
    }

    public void updateHeaderItem(SufNode node) {
        SufHItem hItem = getHItem(node.getId());
        hItem.updateSupport(node.getProbability());
    }


    private SufHItem getHItem(String id) {
        SufHItem result = null;
        for (SufHItem item : itemList) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                result = item;
            }
        }
        return result;
    }
}
