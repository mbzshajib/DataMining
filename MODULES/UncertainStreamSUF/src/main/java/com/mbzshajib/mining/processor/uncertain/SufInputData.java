package com.mbzshajib.mining.processor.uncertain;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/20/2015
 * @time: 9:27 PM
 * ****************************************************************
 */

public class SufInputData {
    private String id;
    private double probability;

    public SufInputData(String id, double probability) {
        this.id = id;
        this.probability = probability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "SufInputData{" +
                "id='" + id + '\'' +
                ", probability=" + probability +
                '}';
    }
}
