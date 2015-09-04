package com.mbzshajib.utility.random;

import java.math.BigDecimal;
import java.util.Random;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/4/2015
 * time: 5:50 PM
 * ****************************************************************
 */


public final class RandomUtility {
    public static final double[] getRandom(double minValue, double maxValue, int count, int precision) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("maxValue must be greater than minValue. maxValue is : " + maxValue + " minValue is : " + minValue);
        }
        double result[] = new double[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double range = maxValue - minValue;
            double tmp = random.nextDouble();
            double tmpDouble = minValue + range * tmp;
            result[i] = new BigDecimal(tmpDouble).setScale(precision, BigDecimal.ROUND_CEILING).doubleValue();
        }
        return result;

    }
}
