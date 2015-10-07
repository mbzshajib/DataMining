package com.mbzshajib.mining.processor.randomdata.generator;

import com.mbzshajib.mining.processor.randomdata.generator.v1.RandomGeneratorInput;
import com.mbzshajib.mining.processor.randomdata.generator.v1.RandomGeneratorOutput;
import com.mbzshajib.mining.processor.randomdata.generator.v1.RandomGeneratorProcessor;
import com.mbzshajib.mining.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/13/2015
 * @time: 9:15 PM
 * ****************************************************************
 */

public class RandomGeneratorUtils {
    private static final String TOTAL_INPUT = "totalInput";
    private static final String MIN_RAND_VALUE = "minRandValue";
    private static final String MAX_RAND_VALUE = "maxRandValue";
    private static final String LOWER_PEAK_VALUE = "lowerPeakValue";
    private static final String UPPER_PEAK_VALUE = "upperPeakValue";
    private static final String PEAK_VALUE_PERCENTAGE = "peakValuePercentage";
    private static final String PRECISION = "precision";

    public static RandomGeneratorOutput simulate(RandomGeneratorInput randomSetGeneratorInput) {
        RandomGeneratorProcessor randomSetGeneratorProcessor = new RandomGeneratorProcessor();
        long startTime = System.currentTimeMillis();
        RandomGeneratorOutput randomSetGeneratorOutput = randomSetGeneratorProcessor.process(randomSetGeneratorInput);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        Utils.log("STARTT TIME", startTime + " UTC");
        Utils.log("END TIME", endTime + " UTC");
        Utils.log("TIME NEEDED", totalTime + " MS");
        return randomSetGeneratorOutput;
    }

    public static RandomGeneratorInput getStaticInput() {
        RandomGeneratorInput randomSetGeneratorInput = new RandomGeneratorInput();
        randomSetGeneratorInput.setPeakValuePercentage((byte) 80);
        randomSetGeneratorInput.setMinRandValue(.30);
        randomSetGeneratorInput.setMaxRandValue(.99);
        randomSetGeneratorInput.setLowerPeakValue(.60);
        randomSetGeneratorInput.setUpperPeakValue(.90);
        randomSetGeneratorInput.setTotalInput(100);
        randomSetGeneratorInput.setPrecision((byte) 2);
        return randomSetGeneratorInput;
    }

    public static RandomGeneratorInput getFileInput(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInputStream);

        RandomGeneratorInput input = new RandomGeneratorInput();
        input.setTotalInput(Integer.parseInt(properties.getProperty(TOTAL_INPUT)));
        input.setLowerPeakValue(Double.parseDouble(properties.getProperty(LOWER_PEAK_VALUE)));
        input.setUpperPeakValue(Double.parseDouble(properties.getProperty(UPPER_PEAK_VALUE)));
        input.setMaxRandValue(Double.parseDouble(properties.getProperty(MAX_RAND_VALUE)));
        input.setMinRandValue(Double.parseDouble(properties.getProperty(MIN_RAND_VALUE)));
        input.setPeakValuePercentage(Byte.parseByte(properties.getProperty(PEAK_VALUE_PERCENTAGE)));
        input.setPrecision(Byte.parseByte(properties.getProperty(PRECISION)));


        return input;
    }

    public static void print(RandomGeneratorInput randomGeneratorInput ,RandomGeneratorOutput randomSetGeneratorOutput) {
        Utils.log("INPUT ",randomGeneratorInput.toString());
        Utils.log("OUTPUT ",randomSetGeneratorOutput.toString());
    }
}
