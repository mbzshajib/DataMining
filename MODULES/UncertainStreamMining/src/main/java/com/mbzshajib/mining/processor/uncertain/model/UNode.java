package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:16 PM
 * ****************************************************************
 */

public class UNode {

    private String id;
    private int frameSize;
    private UNode parentNode;
    private List<UNode> childNodeList;
    private List<UData> uncertainDataList;
    private double miningProbability;
    private double totalSupport;
    private double totalPrefix;

    public double getTotalSupport() {
        if (totalSupport == 0) {
            for (UData data : uncertainDataList) {
                totalSupport += data.getItemProbability();
            }
        }
        return totalSupport;
    }

    public void setTotalSupport(double totalSupport) {
        this.totalSupport = totalSupport;
    }

    public double getTotalPrefix() {
        if (totalPrefix == 0) {
            for (UData data : uncertainDataList) {
                totalPrefix += data.getPrefixValue();
            }
        }
        return totalPrefix;
    }

    public void setTotalPrefix(double totalPrefix) {
        this.totalPrefix = totalPrefix;
    }

    private UNode() {

    }

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

    public void addUData(int frameNo, UData dataToBeAdded) throws DataNotFoundException {
        if (frameNo >= frameSize) {
            throw new DataNotFoundException("Frame no " + frameNo + " must be less than frame size " + frameSize + ".");
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
        stringBuilder.append(Constants.NEW_LINE)
                .append(Constants.HASH)
                .append("Parent ")
                .append("ID:").append(Constants.HASH).append(id);
        stringBuilder.append(Constants.TABBED_HASH)
                .append(Constants.HASH)
                .append("Child ").append(childNodeList.size())
                .append(Constants.TABBED_HASH)
                .append("[ ");
        for (UNode node : childNodeList) {
            stringBuilder
                    .append(Constants.TABBED_HASH)
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
            parentNode.getChildNodeList().remove(this);
            return true;
        } else {
            return false;
        }

    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public void setUncertainDataList(List<UData> uncertainDataList) {
        this.uncertainDataList = uncertainDataList;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public List<UData> getUncertainDataList() {
        return uncertainDataList;
    }

    public UNode copy() {
        UNode nodeToBeCopied = this;
        UNode rootNode = new UNode(new String(nodeToBeCopied.getId()), nodeToBeCopied.getFrameSize());
        rootNode.setParentNode(null);
        for (int i = 0; i < nodeToBeCopied.getChildNodeList().size(); i++) {
            UNode node = nodeToBeCopied.getChildNodeList().get(i);
            UNode copiedNode = copyNodes(node, rootNode);
            rootNode.setChildNode(copiedNode);
        }
        return rootNode;
    }

    private UNode copyNodes(UNode nodeToBeCopied, UNode parentNode) {
        UNode resultNode = new UNode(new String(nodeToBeCopied.getId()), nodeToBeCopied.getFrameSize());
        resultNode.setParentNode(parentNode);
        List<UData> uDataList = new ArrayList<UData>();
        for (UData uData : nodeToBeCopied.getUncertainDataList()) {
            uDataList.add(uData.copy());
        }
        resultNode.setUncertainDataList(uDataList);
        for (int i = 0; i < nodeToBeCopied.getChildNodeList().size(); i++) {
            UNode node = nodeToBeCopied.getChildNodeList().get(i);
            UNode copiedNode = copyNodes(node, resultNode);
            resultNode.setChildNode(copiedNode);
        }
        return resultNode;
    }

    private void setChildNode(UNode childNode) {
        this.childNodeList.add(childNode);
    }

    @Override
    public String toString() {
        return "UNode{" +
                "id='" + id + '\'' +
                '}';
    }

    public double getNodePrefixValue() {
        double result = 0;
        for (UData data : uncertainDataList) {
            result = result + data.getPrefixValue();
        }
        return result;
    }

    public double getItemProbabilityValue() {
        double result = 0;
        for (UData data : uncertainDataList) {
            result = result + data.getItemProbability();
        }
        return result;
    }

    public void removeChildNode(UNode childNode) {
        this.childNodeList.remove(childNode);
    }

    public List<UNode> getDistinctNodes() {
        List<UNode> result = new ArrayList<UNode>();
        for (int index = 0; index < childNodeList.size(); index++) {
            UNode child = childNodeList.get(index);
            if (!result.contains(child)) {
                result.add(child);
            }
            result.addAll(child.getDistinctNodes());
        }
        return result;
    }

    public void removeNodeIfChildOfAnyDepth(UNode node) {
        int count = childNodeList.size();
        for (int i = 0; i < count; i++) {
            UNode child = childNodeList.get(i);
            if (child == node) {
                childNodeList.remove(node);
                i--;
                count--;
            }
            child.removeNodeIfChildOfAnyDepth(node);
        }
    }

    public void removeNodeIfChildOfAnyDepthById(String id) {
        int count = childNodeList.size();
        for (int i = 0; i < count; i++) {
            UNode child = childNodeList.get(i);
            if (child.getId().equalsIgnoreCase(id)) {
                childNodeList.remove(child);
                i--;
                count--;
            } else {
                child.removeNodeIfChildOfAnyDepthById(id);
            }
        }
    }

    public double getMiningProbability() {
        return miningProbability;
    }

    public void setMiningProbability(double miningProbability) {
        this.miningProbability = miningProbability;
    }

    public void getLeafNodeList(List<UNode> list) {
        if (childNodeList.isEmpty()) {
            list.add(this);
            return;
        } else {
            for (int i = 0; i < childNodeList.size(); i++) {
                UNode child = childNodeList.get(i);
                child.getLeafNodeList(list);
            }
        }
    }

    public int countAllChild() {
        int count = childNodeList.size();
        for (UNode node : childNodeList) {
            count = count + node.countAllChild();
        }
        return count;
    }
}
