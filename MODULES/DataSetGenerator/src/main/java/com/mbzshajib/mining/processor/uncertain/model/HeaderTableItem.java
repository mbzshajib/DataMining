package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/21/2015
 * @time: 3:59 PM
 * ****************************************************************
 */


class HeaderTableItem {
    private String itemId;
    private List<UNode> nodeList;

    HeaderTableItem(String itemId) {
        this.itemId = itemId;
        nodeList = new ArrayList<UNode>();
    }

    HeaderTableItem(String itemId, int nodeSize) {
        this.itemId = itemId;
        nodeList = new ArrayList<UNode>(nodeSize);
    }


    void updateHeaderData(UNode uNode) {
        nodeList.add(uNode);
    }

    String getItemId() {
        return itemId;
    }

    void setItemId(String itemId) {
        this.itemId = itemId;
    }

    List<UNode> getNodeList() {
        return nodeList;
    }

    double getItemPrefixValue() {
        double result = 0;
        for (UNode node : nodeList) {
            result = result + node.getNodePrefixValue();
        }
        return result;
    }

    double getItemProbabilityValue() {
        double result = 0;
        for (UNode node : nodeList) {
            result = result + node.getItemProbabilityValue();

        }
        return result;
    }

    double getMiningValue() {
        double result = 0;
        for (UNode node : nodeList) {
            result = result + node.getMiningProbability();

        }
        return result;
    }

    void setNodeList(List<UNode> nodeList) {
        this.nodeList = nodeList;
    }

    String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constant.HASH)
                .append("ID ")
                .append(itemId)
                .append(Constant.TABBED_HASH)
                .append("Data { ").append(Constant.TAB);

        int index = 0;
        for (UNode node : nodeList) {
            stringBuilder.append(Constant.HASH)
                    .append(node.toString()).append(Constant.TAB);
            index++;
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    int getNodeIndex(UNode node) {
        int index = -1;
        for (int i = 0; i < nodeList.size(); i++) {
            if (node == nodeList.get(i)) {
                index = i;
                break;
            }

        }
        return index;
    }

    void addNodeItem(UNode node, int index) {
        //TODO: Later UPdate this in the table.
//        if (nodeList.size() <= index) {
//            for (int i = nodeList.size() - 1; i < index; i++) {
//                nodeList.add(null);
//            }
//        }
        nodeList.add(node);
    }

    @Override
    public String toString() {
        return "HeaderTableItem{" +
                "itemId='" + itemId + '\'' +
                ", nodeCount=" + nodeList.size() +
                '}';
    }
}
