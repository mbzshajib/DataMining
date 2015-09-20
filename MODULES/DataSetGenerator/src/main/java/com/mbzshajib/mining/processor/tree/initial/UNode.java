package com.mbzshajib.mining.processor.tree.initial;

import com.mbzshajib.mining.exception.DataNotValidException;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/20/2015
 * time: 11:16 PM
 * ****************************************************************
 */

public class UNode {
    private int nodeCount;
    private UNode parentNode;
    private List<UNode> childNodeList;
    private double pValue;
    private String id;

    /**
     * Must be in following formate
     * ID-pVal
     */
    public UNode(String uNodeValueInString) throws DataNotValidException {
        String[] splitedVals = uNodeValueInString.split("-");
        if (splitedVals.length != 2) {
            throw new DataNotValidException("Each node value must be in \'ID-pVal\' found value is " + uNodeValueInString);
        }
        this.id = splitedVals[0];
        try {
            this.pValue = Double.parseDouble(splitedVals[1]);
        } catch (NumberFormatException e) {
            throw new DataNotValidException(e);
        }
        childNodeList = new ArrayList<UNode>();
    }

    public UNode getParentNode() {
        return parentNode;
    }

    public List<UNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<UNode> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public void setParentNode(UNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getpValue() {
        return pValue;
    }

    public void setpValue(double pValue) {
        this.pValue = pValue;
    }

    public void addChild(UNode node) {
        childNodeList.add(node);
    }

    public int getChildNodeCount() {
        return childNodeList.size();
    }

    public boolean isSameID(UNode node) {
        return (this.id.equals(node.getId()));
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }


}
