package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.exception.DataNotValidException;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/28/2015
 * time: 8:41 PM
 * ****************************************************************
 */

public class UncertainTree {
    private UNode rootNode;
    private HeaderTable headerTable;
    private int frameSize;
    private int windowSize;

    public UncertainTree(int frameSize, int windowSize) throws DataNotValidException {
        this.frameSize = frameSize;
        this.windowSize = windowSize;
        this.headerTable = new HeaderTable(windowSize);
        this.rootNode = new UNode("0-0");
        this.rootNode.setParentNode(null);
        this.rootNode.setPrefixValue(0);
    }

    public String getTraversedString() {
        return rootNode.traverse();
    }

    public UNode getRootNode() {
        return rootNode;
    }

    public HeaderTable getHeaderTable() {
        return headerTable;
    }

    public void addTransactionToTree(List<UNode> nodeList) throws DataNotValidException {
        UNode parentNode = rootNode;
        for (UNode node : nodeList) {
            parentNode = addNode(node, parentNode);
        }
    }

    public void slideWindowAndUpdateTree() {
        removeOneFrameOldestData(rootNode);
        updateFrameNumber(rootNode);
        headerTable.slideWindowAndUpdateTable();
    }

    private void removeOneFrameOldestData(UNode node) {
        for (int i = 0; i < node.getChildNodeCount(); i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            if (tmpNode.getFrameNo() == 0) {
                node.getChildNodeList().remove(tmpNode);
                i=-1;
            }
        }
    }

    private void updateFrameNumber(UNode node) {
        for (int i = 0; i < node.getChildNodeCount(); i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            tmpNode.setFrameNo(tmpNode.getFrameNo() - 1);
            node.getChildNodeList().set(i, tmpNode);
            updateFrameNumber(tmpNode);
        }

    }

    private UNode addNode(UNode node, UNode parentNode) throws DataNotValidException {
        int foundIndex = -1;
        for (int i = 0; i < parentNode.getChildNodeList().size(); i++) {
            if (parentNode.getChildNodeList().get(i).isSameIdAndFrameNo(node)) {
                foundIndex = i;
            }
        }
        if (foundIndex == -1) {
            parentNode.addChild(node);
            headerTable.updateHeaderTable(node);
            return node;
        } else {
            UNode tmpNode = parentNode.getChildNodeList().get(foundIndex);
            double newPrefixValue = tmpNode.getPrefixValue() + node.getPrefixValue();
            double newItemProbability = tmpNode.getItemProbability() + node.getItemProbability();
            tmpNode.setPrefixValue(newPrefixValue);
            tmpNode.setItemProbability(newItemProbability);
            parentNode.getChildNodeList().set(foundIndex, tmpNode);
            return parentNode.getChildNodeList().get(foundIndex);
        }
    }
}
