package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.exception.DataNotValidException;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/21/2015
 * time: 3:59 PM
 * ****************************************************************
 */


public class HeaderTableItem {
    private String itemId;
    private int windowSize;
    private List<HeaderData> headerDataList;

    public HeaderTableItem(String itemId, int windowSize) {
        this.itemId = itemId;
        this.windowSize = windowSize;
        this.headerDataList = new ArrayList<HeaderData>();
        for (int i = 0; i < windowSize; i++) {
            HeaderData data = new HeaderData();
            headerDataList.add(i, data);
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void updateHeaderData(UNode uNode) throws DataNotValidException {
        if (windowSize < uNode.getFrameNo()) {
            throw new DataNotValidException("Node Frame no is " + uNode.getFrameNo() + " it should less than window size " + windowSize);
        }
        HeaderData headerData = headerDataList.get(uNode.getFrameNo());
        headerData.addNewNode(uNode);
    }

    public void slideWindowAndUpdateItem() {
        HeaderData headerData = headerDataList.get(0);
        headerDataList.remove(headerData);
        headerDataList.add(new HeaderData());
    }

    public double getTotalSupport() {
        double totalSupport = 0;
        for (HeaderData headerData : headerDataList) {
            for (UNode node : headerData.getNodes()) {
                totalSupport += node.getItemProbability();
            }
        }
        return totalSupport;
    }

    public double getTotalPrefixValue() {
        double totalPrefixValue = 0;
        for (HeaderData headerData : headerDataList) {
            for (UNode node : headerData.getNodes()) {
                totalPrefixValue += node.getPrefixValue();
            }
        }
        return totalPrefixValue;
    }


    private class HeaderData {
        List<UNode> nodes;

        HeaderData() {
            nodes = new ArrayList<UNode>();
        }

        void addNewNode(UNode uNode) {
            nodes.add(uNode);
        }

        List<UNode> getNodes() {
            return nodes;
        }

    }

    @Override
    public String toString() {
        return "HeaderTableItem{" +
                "itemId='" + itemId + '\'' +
                ", windowSize=" + windowSize +
                ", headerDataList=" + headerDataList +
                '}';
    }
}
