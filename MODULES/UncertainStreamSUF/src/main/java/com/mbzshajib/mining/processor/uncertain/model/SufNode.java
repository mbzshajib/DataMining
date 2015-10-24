package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.utility.common.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/20/2015
 * @time: 9:05 PM
 * ****************************************************************
 */

public class SufNode {
    private String id;
    private SufNode parentNode;
    private List<SufNode> childes;
    private double probability;
    private double miningProbability;
    private int windowSize;
    private int batchSize;
    private int[] bucket;

    public SufNode(String id, double probability, int windowSize, int batchSize) {
        this.id = id;
        this.probability = probability;
        this.windowSize = windowSize;
        this.batchSize = batchSize;
        childes = new ArrayList<>();
        bucket = new int[windowSize];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SufNode getParentNode() {
        return parentNode;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int[] getBucket() {
        return bucket;
    }

    public void setBucket(int[] bucket) {
        this.bucket = bucket;
    }

    public void setParentNode(SufNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<SufNode> getChildes() {
        return childes;
    }

    public void setChildes(List<SufNode> childes) {
        this.childes = childes;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }


    public void updateCounter(int batchNo) {
        this.bucket[batchNo] = this.bucket[batchNo] + 1;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("P-").append(this.getId());
        stringBuilder.append("\t").append("[");
        for (SufNode node : childes) {
            stringBuilder.append(Constants.TABBED_HASH).append("C-").append(node.getId()).append(" ").append("V(").append(node.getProbability()).append(") ").append("counts " + Arrays.toString(node.getBucket()));
        }
        stringBuilder.append("]");
        stringBuilder.append("\n");
        for (SufNode node : childes) {
            stringBuilder.append(node.traverse());
        }
        return stringBuilder.toString();
    }

    public SufNode copy() {
        SufNode newRoot = new SufNode(this.id, this.probability, this.windowSize, this.batchSize);
        copy(this, newRoot);

        return newRoot;
    }

    private void copy(SufNode originalNode, SufNode copiedNode) {
        for (SufNode node : originalNode.getChildes()) {
            SufNode newChild = new SufNode(node.getId(), node.getProbability(), node.getWindowSize(), node.getBatchSize());
            int[] newBucket = new int[node.getBucket().length];
            for (int i = 0; i < newBucket.length; i++) {
                newBucket[i] = node.getBucket()[i];
            }
            newChild.setBucket(newBucket);
            newChild.setParentNode(copiedNode);
            copiedNode.getChildes().add(newChild);
            copy(node, newChild);
        }
    }

    @Override
    public String toString() {
        return "SufNode{" +
                "id='" + id + '\'' +
                ", parentNode=" + parentNode.getId() +
                ", childes=" + childes.size() +
                ", probability=" + probability +
                ", miningProbability=" + miningProbability +
                ", windowSize=" + windowSize +
                ", batchSize=" + batchSize +
                ", bucket=" + Arrays.toString(bucket) +
                '}';
    }

    public List<SufNode> getAllDistinctChild(List<SufNode> list) {
        list.addAll(childes);
        for (SufNode node : childes) {
            node.getAllDistinctChild(list);
        }
        return list;
    }

    public double getTotalCount() {
        int result = 0;
        for (int count = 0; count < bucket.length; count++) {
            result = result + bucket[count];
        }
        return result;
    }

    public double getMiningProbability() {
        return miningProbability;
    }

    public void setMiningProbability(double miningProbability) {
        this.miningProbability = miningProbability;
    }

    public double getSupport() {
        int count = 0;
        for (int i = 0; i < bucket.length; i++) {
            count += bucket[i];
        }
        return count * probability;

    }

    public void getLeafNodeList(List<SufNode> list) {
        if (childes.isEmpty()) {
            list.add(this);
            return;
        } else {
            for (int i = 0; i < childes.size(); i++) {
                SufNode child = childes.get(i);
                child.getLeafNodeList(list);
            }
        }

    }

    public void slide() {
        for (int i = 1; i < windowSize; i++) {
            bucket[i - 1] = bucket[i];
        }
        bucket[windowSize - 1] = 0;
        for (SufNode child : childes) {
            child.slide();
        }
    }

    public int countAllChild() {
        int count = childes.size();
        for (SufNode node : childes) {
            count = count + node.countAllChild();
        }
        return count;
    }
}
