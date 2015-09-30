package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.util.Constant;

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

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constant.TABBED_HASH)
                .append("Item ID : ")
                .append(itemId);
        if (itemId.length() == 1) {
            stringBuilder.append("  ");
        } else if (itemId.length() == 2) {
            stringBuilder.append(" ");
        }

        stringBuilder.append(Constant.TABBED_HASH)
                .append("Window Size : ")
                .append(windowSize)
                .append(Constant.TABBED_HASH)
                .append("Header Data -> (");

        for (HeaderData data : headerDataList) {
            stringBuilder.append(data.traverse());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public HeaderInfo getHeaderInfo() {
        String id = this.itemId;
        double support = 0;
        double prefixValue = 0;
        for (HeaderData data : headerDataList) {
            for (UNode node : data.getNodes()) {
                support = support + node.getItemProbability();
                prefixValue = prefixValue + node.getPrefixValue();
            }
        }
        HeaderInfo info = new HeaderInfo();
        info.setId(id);
        info.setSupportValue(support);
        info.setPrefixValue(prefixValue);
        return info;

    }

    public List<UNode> getAllNodes() {
        List<UNode> result = new ArrayList<UNode>();
        for (HeaderData data : headerDataList) {
            for (UNode uNode : data.getNodes()) {
                result.add(uNode);
            }
        }
        return result;
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

        String traverse() {
            StringBuilder stringBuilder = new StringBuilder();
            for (UNode node : nodes) {
                stringBuilder.append(node.toString());
            }
            return stringBuilder.toString();
        }

    }

    public boolean isEmpty() {
        boolean isEmpty = true;
        for (HeaderData data : headerDataList) {
            if (data.getNodes().size() > 0) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
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
