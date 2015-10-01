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
 * date: 9/20/2015
 * time: 11:16 PM
 * ****************************************************************
 */

public class UNode {

    private String id;
    private int frameSize;
    private UNode parentNode;
    private List<UNode> childNodeList;

    private List<UData> uncertainDataList;

    public UNode(String id, int frameSize) {
        this.id = id;
        this.frameSize = frameSize;
        childNodeList = new ArrayList<UNode>();
        uncertainDataList = new ArrayList<UData>(frameSize);
        for (int i = 0; i < frameSize; i++) {
            UData uData = new UData(0, 0);
            uncertainDataList.add(i, uData);
        }
    }

    public void addUData(int frameNo, UData dataToBeAdded) throws DataNotValidException {
        if (frameNo >= frameSize) {
            throw new DataNotValidException("Frame no " + frameNo + " must be less than frame size " + frameSize + ".");
        }
        UData uData = uncertainDataList.get(frameNo);
        uData.setPrefixValue(uData.getPrefixValue() + dataToBeAdded.getPrefixValue());
        uData.setItemProbability(uData.getItemProbability() + dataToBeAdded.getItemProbability());
    }

    public void addChild(UNode childNode) {
        childNode.setParentNode(this);
        childNodeList.add(childNode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(UNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<UNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<UNode> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constant.NEW_LINE)
                .append(Constant.HASH).append(Constant.HASH)
                .append("Parent ")
                .append("ID:").append(Constant.HASH).append(id);
        stringBuilder.append(Constant.TABBED_HASH)
                .append(Constant.HASH)
                .append("Child ").append(childNodeList.size())
                .append(Constant.TABBED_HASH)
                .append("[ ");
        for (UNode node : childNodeList) {
            stringBuilder
                    .append(Constant.TABBED_HASH)
                    .append(node.getId())
                    .append("[");
            for (UData uData : node.getUncertainDataList()) {
                stringBuilder.append("{")
                        .append("S-").append(uData.getItemProbability())
                        .append("}");
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" ]");
        for (UNode node : childNodeList) {
            stringBuilder.append(node.traverse());
        }
        return stringBuilder.toString();
    }

    public boolean isSameId(String id) {
        return id.equalsIgnoreCase(this.id);
    }

    public void slide() {
        this.uncertainDataList.remove(this.uncertainDataList.get(0));
        this.uncertainDataList.add(new UData(0, 0));

        for (UNode node : childNodeList) {
            node.slide();
        }
    }

    public boolean removeNodeIfEmpty() {
        boolean isEmpty = true;
        for (UData data : uncertainDataList) {
            if (data.getPrefixValue() > 0) {
                isEmpty = false;
                break;
            }

        }
        if (isEmpty) {
            System.out.printf("Removing Node ID " + id);
            parentNode.getChildNodeList().remove(this);
            return true;
        } else {
            return false;
        }

    }

    public List<UData> getUncertainDataList() {
        return uncertainDataList;
    }

    @Override
    public String toString() {
        return "UNode{" +
                "id='" + id + '\'' +
                ", parentNode=" + parentNode +
                '}';
    }
}