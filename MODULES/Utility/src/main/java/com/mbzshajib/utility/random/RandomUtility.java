package com.mbzshajib.utility.random;

import java.math.BigDecimal;
import java.util.Random;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 5:50 PM
 * ****************************************************************
 */


public final class RandomUtility {
    public static final double getRandom(double minValue, double maxValue, int precision) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("maxValue must be greater than minValue. maxValue is : " + maxValue + " minValue is : " + minValue);
        }
        double result;
        Random random = new Random();
        double range = maxValue - minValue;
        double tmp = random.nextDouble();
        double tmpDouble = minValue + range * tmp;
        result = new BigDecimal(tmpDouble).setScale(precision, BigDecimal.ROUND_CEILING).doubleValue();
        return result;

    }

    public static final double[] getRandoms(double minValue, double maxValue, int count, byte precision) {
        double result[] = new double[count];
        for (int i = 0; i < count; i++) {
            result[i] = getRandom(minValue, maxValue, precision)                ;
        }
        return result;

    }
}
