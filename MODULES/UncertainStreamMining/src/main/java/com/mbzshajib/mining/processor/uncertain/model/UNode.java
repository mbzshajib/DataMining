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
        totalSupport = 0;
        for (UData data : uncertainDataList) {
            totalSupport += data.getItemProbability();
        }

        return totalSupport;
    }

    public void setTotalSupport(double totalSupport) {
        this.totalSupport = totalSupport;
    }

    public double getTotalPrefix() {
        totalPrefix = 0;
        for (UData data : uncertainDataList) {
            totalPrefix += data.getPrefixValue();
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

    public String traverseMin() {
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
                    .append(node.getId());
        }
        stringBuilder.append(" ]");
        for (UNode node : childNodeList) {
            stringBuilder.append(node.traverseMin());
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
        UNode originParent = this;
        UNode copyParent = new UNode(new String(originParent.getId()), originParent.getFrameSize());
        copyParent.setParentNode(null);
        for (int i = 0; i < originParent.getChildNodeList().size(); i++) {
            UNode originChild = originParent.getChildNodeList().get(i);
            UNode copiedChild = copyNodes(originChild, copyParent);
            copyParent.setChildNode(copiedChild);
        }
        return copyParent;
    }

    private UNode copyNodes(UNode originChild, UNode copyParent) {
        UNode copyChild = new UNode(new String(originChild.getId()), originChild.getFrameSize());
        copyChild.setTotalSupport(originChild.getTotalSupport());
        copyChild.setTotalPrefix(originChild.getTotalPrefix());
        copyChild.setMiningProbability(originChild.getMiningProbability());
        copyChild.setParentNode(copyParent);
        List<UData> uDataList = new ArrayList<UData>();
        for (UData uData : originChild.getUncertainDataList()) {
            uDataList.add(uData.copy());
        }
        copyChild.setUncertainDataList(uDataList);
        for (int i = 0; i < originChild.getChildNodeList().size(); i++) {
            UNode node = originChild.getChildNodeList().get(i);
            UNode copiedNode = copyNodes(node, copyChild);
            copyChild.setChildNode(copiedNode);
        }
        return copyChild;
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
