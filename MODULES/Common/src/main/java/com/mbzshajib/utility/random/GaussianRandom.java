package com.mbzshajib.utility.random;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/12/2015
 * @time: 8:58 AM
 * ****************************************************************
 */


public class GaussianRandom {
    private Random random;
    private double minValue;
    private double variance;

    public GaussianRandom(double minValue, double variance) {
        this.minValue = minValue;
        this.variance = variance;
        random = new Random();
    }

    public List<Double> getGaussianRandoms(int count, int precision) {
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < count; i++) {
            double rand = Math.abs(minValue + random.nextGaussian() * variance);
            rand = new BigDecimal(rand).setScale(precision, BigDecimal.ROUND_CEILING).doubleValue();
            if (rand > 1) {
                rand = rand - 1;
            }
            result.add(rand);
        }
        return result;
    }

    public static void main(String[] args) {
        double min = .5;
        double variance = .5 / Math.PI;
        GaussianRandom gaussianRandom = new GaussianRandom(min, variance);
        List<Double> randoms = gaussianRandom.getGaussianRandoms(1000,2);
        for (double random : randoms) {
            System.out.println("" + random);
        }

    }
}
