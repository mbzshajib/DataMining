package com.mbzshajib.mining.processor.uncertain.model;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/3/2015
 * @time: 9:53 PM
 * ****************************************************************
 */

public class HTableItemInfo {
    String itemId;
    double itemProbabilityValue;
    double itemPrefixValue;
    double miningProbability;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getItemProbabilityValue() {
        return itemProbabilityValue;
    }

    public void setItemProbabilityValue(double itemProbabilityValue) {
        this.itemProbabilityValue = itemProbabilityValue;
    }

    public double getItemPrefixValue() {
        return itemPrefixValue;
    }

    public void setItemPrefixValue(double itemPrefixValue) {
        this.itemPrefixValue = itemPrefixValue;
    }

    public double getMiningProbability() {
        return miningProbability;
    }

    public void setMiningProbability(double miningProbability) {
        this.miningProbability = miningProbability;
    }

    @Override
    public String toString() {
        return "HeaderInfo{" +
                "itemId='" + itemId + '\'' +
                ", Probability=" + itemProbabilityValue +
                ", Prefix=" + itemPrefixValue +
                '}';
    }
}
