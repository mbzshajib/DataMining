package com.mbzshajib.mining.processor.uncertain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/30/2015
 * time: 5:11 PM
 * ****************************************************************
 */

public class MiningNode {
    private String id;
    private double prefixValue;
    private double supportValue;
    private MiningNode parent;
    private List<MiningNode> childList;

    public MiningNode(String id) {
        this.id = id;
        childList = new ArrayList<MiningNode>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(double prefixValue) {
        this.prefixValue = prefixValue;
    }

    public double getSupportValue() {
        return supportValue;
    }

    public void setSupportValue(double supportValue) {
        this.supportValue = supportValue;
    }

    public MiningNode getParent() {
        return parent;
    }

    public void setParent(MiningNode parent) {
        this.parent = parent;
    }

    public List<MiningNode> getChildList() {
        return childList;
    }

    public void addChild(MiningNode node) {
        node.setParent(this);
        childList.add(node);
    }

}
