package com.mbzshajib.mining.processor.uncertain;

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
    private int windowSize;
    private int batchSize;
    private int[] bucket;

    public SufNode(String id,double probability ,int windowSize, int batchSize) {
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
        this.bucket[batchNo]=this.bucket[batchNo]+1;
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

    @Override
    public String toString() {
        return "SufNode{" +
                "id='" + id + '\'' +
                ", parentNode=" + parentNode +
                ", childes=" + childes.size() +
                ", probability=" + probability +
                ", windowSize=" + windowSize +
                ", batchSize=" + batchSize +
                ", bucket=" + Arrays.toString(bucket) +
                '}';
    }
}
