package com.mbzshajib.utility.common;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 4:32 PM
 * ****************************************************************
 */

public class PrintUtils {
    public static String getDoubleArrayPrintedValues(double[] input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            stringBuilder.append("index[" + i + "]");
            stringBuilder.append(":");
            stringBuilder.append(input[i]);
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }
}
