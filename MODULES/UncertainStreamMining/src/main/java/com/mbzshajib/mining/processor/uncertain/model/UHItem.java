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
 * @date: 10/29/2015
 * @time: 8:22 PM
 * ****************************************************************
 */

public class UHItem {
    private String itemId;
    private double totalSupport;
    private double totalPrefix;
    private double totalMiningVal;
    private List<UNode> nodeList;

    public UHItem(String itemId) {
        this.itemId = itemId;
        this.nodeList = new ArrayList<UNode>();
    }

    public void setInitialValue(double totalSupport, double totalPrefix, double miningProbability) {
        this.totalSupport = totalSupport;
        this.totalPrefix = totalPrefix;
        this.totalMiningVal = miningProbability;
    }

    public void updateValues(double totalSupport, double totalPrefix, double miningProbability) {
        this.totalSupport += totalSupport;
        this.totalPrefix += totalPrefix;
        this.totalMiningVal += miningProbability;
    }

    public double getTotalSupport() {
        return totalSupport;
    }

    public void setTotalSupport(double totalSupport) {
        this.totalSupport = totalSupport;
    }

    public double getTotalPrefix() {
        return totalPrefix;
    }

    public void setTotalPrefix(double totalPrefix) {
        this.totalPrefix = totalPrefix;
    }

    public double getTotalMiningVal() {
        return totalMiningVal;
    }

    public void setTotalMiningVal(double totalMiningVal) {
        this.totalMiningVal = totalMiningVal;
    }

    public List<UNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<UNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void addNode(UNode node) {
        this.nodeList.add(node);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: ").append(itemId);
        stringBuilder.append(" Total Support : ").append(totalSupport);
        stringBuilder.append(" Total Prefix : ").append((totalPrefix));
        stringBuilder.append(" Total Mining : ").append(totalMiningVal);
        return stringBuilder.toString();

    }

    @Override
    public String toString() {
        return "UHItem{" +
                "itemId='" + itemId + '\'' +
                ", totalSupport=" + totalSupport +
                ", totalPrefix=" + totalPrefix +
                ", totalMiningVal=" + totalMiningVal +
                ", nodeList=" + nodeList +
                '}';
    }
}
