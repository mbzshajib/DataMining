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
 * @time: 9:06 PM
 * ****************************************************************
 */

public class SufTree {
    private final int windowSize;
    private final int batchSize;
    private SufNode rootNode;
    private SufHeader header;

    public SufTree(int windowSize, int batchSize) {
        this.batchSize = batchSize;
        this.windowSize = windowSize;

        this.rootNode = new SufNode("0", 0.0, windowSize, batchSize);
        this.header = new SufHeader();

    }


    public void addTransactionToTree(List<SufInputData> nodes, int frameNo) {
        SufNode parentNode = rootNode;
        for (SufInputData data : nodes) {
            parentNode = addNode(parentNode, data, frameNo);

        }
    }

    public SufNode addNode(SufNode itaratingNode, SufInputData data, int batchNo) {
        SufNode resultNode = null;
        int foundIndex = -1;
        for (int i = 0; i < itaratingNode.getChildes().size(); i++) {
            SufNode node = itaratingNode.getChildes().get(i);
            if (node.getId().equalsIgnoreCase(data.getId()) && data.getProbability() == node.getProbability()) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1) {
            resultNode = new SufNode(data.getId(), data.getProbability(), this.windowSize, this.batchSize);
            resultNode.updateCounter(batchNo);
            itaratingNode.getChildes().add(resultNode);
            resultNode.setParentNode(itaratingNode);
            header.addNewNodeToHeader(resultNode);
        } else {
            resultNode = itaratingNode.getChildes().get(foundIndex);
            resultNode.updateCounter(batchNo);
            header.updateHeaderItem(resultNode);
        }
        return resultNode;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public SufNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(SufNode rootNode) {
        this.rootNode = rootNode;
    }

    public String traverse() {
        return rootNode.traverse();
    }

    public SufHeader getHeader() {
        return header;
    }

    public void setHeader(SufHeader header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "SufTree{" +
                "windowSize=" + windowSize +
                ", batchSize=" + batchSize +
                ", rootNode=" + rootNode +
                '}';
    }

    public SufTree copy() {
        SufNode newRoot = rootNode.copy();
        SufHeader header = createHeader(newRoot);
        SufTree sufTree = new SufTree(this.windowSize, this.batchSize);
        sufTree.setRootNode(newRoot);
        sufTree.setHeader(header);
        return sufTree;
    }

    private SufHeader createHeader(SufNode copy) {
        SufHeader sufHeader = new SufHeader();
        List<SufNode> distinctList = copy.getAllDistinctChild(new ArrayList<SufNode>());

        sufHeader.updateFromAllNodes(distinctList);
        return sufHeader;
    }

    public void slideWindowAndUpdateTree() {
        for (SufNode node : rootNode.getChildes()) {
            node.slide();
        }
        removeEmptyChildNodes(rootNode);
    }

    private void removeEmptyChildNodes(SufNode rootNode) {
        int counter = rootNode.getChildes().size();
        for (int i = 0; i < counter; i++) {
            SufNode node = rootNode.getChildes().get(i);
            boolean isDeleted = (node.getSupport() <= 0);
            if (isDeleted) {
                node.getParentNode().getChildes().remove(node);
                node.setParentNode(null);
                counter = counter - 1;
                i = i - 1;
            } else {
                removeEmptyChildNodes(node);
            }
        }
    }
}
