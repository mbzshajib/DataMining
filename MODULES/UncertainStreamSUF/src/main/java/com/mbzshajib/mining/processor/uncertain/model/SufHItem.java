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
 * @date: 10/20/2015
 * @time: 11:33 PM
 * ****************************************************************
 */

public class SufHItem {
    private String itemId;
    private double totalSupport;
    private double miningSupport;
    private List<SufNode> nodeList;

    public SufHItem(String id) {
        this.itemId = id;
        this.nodeList = new ArrayList<SufNode>();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<SufNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<SufNode> nodeList) {
        this.nodeList = nodeList;
    }

    public double getTotalSupport() {
        return totalSupport;
    }

    public void setTotalSupport(double totalSupport) {
        this.totalSupport = totalSupport;
    }

    public double getMiningSupport() {
        return miningSupport;
    }

    public void setMiningSupport(double miningSupport) {
        this.miningSupport = miningSupport;
    }

    public void updateSupport(double probability) {
        this.totalSupport = this.totalSupport + probability;
    }

    public void removeNodesFromTree() {
        int size = nodeList.size();
        for (int i = 0; i < size; i++) {
            SufNode node = nodeList.get(i);
            SufNode parentNode = node.getParentNode();
            parentNode.getChildes().remove(node);
        }
    }

    @Override
    public String toString() {
        return "SufHItem{" +
                "itemId='" + itemId + '\'' +
                ", totalSupport=" + totalSupport +
                '}';
    }

    public void updateMiningSupport(double miningProbability) {
        this.miningSupport = this.miningSupport + miningProbability;
    }
}
