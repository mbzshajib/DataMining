package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.util.Constant;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/28/2015
 * @time: 8:41 PM
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
        this.rootNode = new UNode("0", windowSize);
        this.rootNode.setParentNode(null);
    }

    public String getTraversedString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UNCERTAIN TREE")
                .append(Constant.NEW_LINE)
                .append(Constant.NEW_LINE);
        builder.append(rootNode.traverse());
        builder.append(Constant.NEW_LINE);

        builder.append("HEADER TABLE")
                .append(Constant.NEW_LINE)
                .append(Constant.NEW_LINE);
        builder.append(headerTable.traverse());
        builder.append(Constant.NEW_LINE);
        return builder.toString();
    }

    public UNode getRootNode() {
        return rootNode;
    }

    public HeaderTable getHeaderTable() {
        return headerTable;
    }

    public void addTransactionToTree(List<UInputData> uInputData, int frameNo) throws DataNotValidException {
        UNode parentNode = rootNode;
        for (UInputData inputData : uInputData) {
            parentNode = addNode(inputData, parentNode, frameNo);
        }
    }

    public void slideWindowAndUpdateTree() {
        removeOneFrameOldestData(rootNode);
        headerTable.slideHeaderTable();
    }

    private void removeOneFrameOldestData(UNode node) {
        for (int i = 0; i < node.getChildNodeList().size(); i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            tmpNode.slide();
        }
        removeEmptyChildNodes(node);
    }

    private void removeEmptyChildNodes(UNode node) {
        int loopCounter = node.getChildNodeList().size();
        for (int i = 0; i < loopCounter; i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            boolean isDeleted = tmpNode.removeNodeIfEmpty();
            if (isDeleted) {
                loopCounter = loopCounter - 1;
                i = -1;
            } else {
                removeEmptyChildNodes(tmpNode);

            }
        }
    }

    private UNode addNode(UInputData uInputData, UNode parentNode, int frameNo) throws DataNotValidException {
        int foundIndex = -1;
        for (int i = 0; i < parentNode.getChildNodeList().size(); i++) {
            if (parentNode.getChildNodeList().get(i).isSameId(uInputData.getId())) {
                foundIndex = i;
            }
        }
        if (foundIndex == -1) {
            UNode node = new UNode(uInputData.getId(), windowSize);
            parentNode.addChild(node);
            headerTable.updateHeaderTable(node);
            UData uData = new UData(uInputData.getItemPValue(), uInputData.getPrefixValue());
            node.addUData(frameNo, uData);
            return node;
        } else {
            UNode tmpNode = parentNode.getChildNodeList().get(foundIndex);
            UData uData = new UData(uInputData.getItemPValue(), uInputData.getPrefixValue());
            tmpNode.addUData(frameNo, uData);
            return tmpNode;
        }
    }

    public void setRootNode(UNode rootNode) {
        this.rootNode = rootNode;
    }

    public void setHeaderTable(HeaderTable headerTable) {
        this.headerTable = headerTable;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public UncertainTree copy() throws DataNotValidException {
        UncertainTree uncertainTree = new UncertainTree(frameSize, windowSize);
        UNode copiedNode = rootNode.copy();
        HeaderTable headerTable = this.headerTable.copy();
        copyHeaderTable(this.headerTable, headerTable, this.rootNode, copiedNode);
        uncertainTree.setRootNode(copiedNode);
        uncertainTree.setHeaderTable(headerTable);
        return uncertainTree;
    }

    private HeaderTable copyHeaderTable(HeaderTable mainTable, HeaderTable clonedTable, UNode mainNode, UNode clonedNode) {
        for (int i = 0; i < mainNode.getChildNodeList().size(); i++) {
            UNode nodeOriginal = mainNode.getChildNodeList().get(i);
            UNode nodeCopied = clonedNode.getChildNodeList().get(i);

            int index = mainTable.findNode(nodeOriginal);
            if (index == -1) {
                continue;
            } else {

                clonedTable.addNode(nodeCopied, index);
            }
            copyHeaderTable(mainTable, clonedTable, nodeOriginal, nodeCopied);
        }
        return clonedTable;
    }
}
