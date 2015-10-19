package com.mbzshajib.mining.dataset.uncertain;

import com.mbzshajib.utility.file.FileUtility;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/17/2015
 * @time: 12:52 PM
 * ****************************************************************
 */

public class RandomGraphGenerator {


    public static void main(String[] args) throws IOException {
        Map<String, Integer> resultMap = new TreeMap<String, Integer>();

        for (double i = 0; i < 1.01; i += .05) {
            String key = getKeyAsString(i);
            resultMap.put(key, 0);
        }
        List<Double> doubles = FileUtility.readDoublesFromFileAsList("Input/DataGenerationInput/kosarak_randoms.dat");
        for (Double val : doubles) {
            if (val > 0 && val < .05) {
                int newVal = resultMap.get("0.05") + 1;
                resultMap.put("0.05", newVal);
            } else if (val > .05 && val < .10) {
                int newVal = resultMap.get("0.1") + 1;
                resultMap.put("0.1", newVal);
            } else if (val > .10 && val < .15) {
                int newVal = resultMap.get("0.15") + 1;
                resultMap.put("0.15", newVal);
            } else if (val > .15 && val < .20) {
                int newVal = resultMap.get("0.2") + 1;
                resultMap.put("0.2", newVal);
            } else if (val > .20 && val < .25) {
                int newVal = resultMap.get("0.25") + 1;
                resultMap.put("0.25", newVal);
            } else if (val > .25 && val < .30) {
                int newVal = resultMap.get("0.3") + 1;
                resultMap.put("0.3", newVal);
            } else if (val > .30 && val < .35) {
                int newVal = resultMap.get("0.35") + 1;
                resultMap.put("0.35", newVal);
            } else if (val > .35 && val < .40) {
                int newVal = resultMap.get("0.4") + 1;
                resultMap.put("0.4", newVal);
            } else if (val > .40 && val < .45) {
                int newVal = resultMap.get("0.45") + 1;
                resultMap.put("0.45", newVal);
            } else if (val > .45 && val < .50) {
                int newVal = resultMap.get("0.5") + 1;
                resultMap.put("0.5", newVal);
            } else if (val > .50 && val < .55) {
                int newVal = resultMap.get("0.55") + 1;
                resultMap.put("0.55", newVal);
            } else if (val > .55 && val < .60) {
                int newVal = resultMap.get("0.6") + 1;
                resultMap.put("0.6", newVal);
            } else if (val > .60 && val < .65) {
                int newVal = resultMap.get("0.65") + 1;
                resultMap.put("0.65", newVal);
            } else if (val > .65 && val < .70) {
                int newVal = resultMap.get("0.7") + 1;
                resultMap.put("0.7", newVal);
            } else if (val > .70 && val < .75) {
                int newVal = resultMap.get("0.75") + 1;
                resultMap.put("0.75", newVal);
            } else if (val > .75 && val < .80) {
                int newVal = resultMap.get("0.8")+1;
                resultMap.put("0.8", newVal);
            } else if (val > .80 && val < .85) {
                int newVal = resultMap.get("0.85") + 1;
                resultMap.put("0.85", newVal);
            } else if (val > .85 && val < .90) {
                int newVal = resultMap.get("0.9") + 1;
                resultMap.put("0.9", newVal);
            } else if (val > .90 && val < .95) {
                int newVal = resultMap.get("0.95") + 1;
                resultMap.put("0.95", newVal);
            } else if (val > .95 && val < 1.0) {
                int newVal = resultMap.get("1") + 1;
                resultMap.put("1", newVal);
            }
        }
        Set<String> strings = resultMap.keySet();
        for (String string : strings) {
            System.out.println(string + "\t\t" + resultMap.get(string));
        }
    }

    private static String getKeyAsString(double i) {
        DecimalFormat df = new DecimalFormat("#.##");
        return "" + df.format(i);
    }
}
