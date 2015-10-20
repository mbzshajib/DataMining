package com.mbzshajib.mining.processor.uncertain;

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
}
