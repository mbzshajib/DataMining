package com.mbzshajib.mining.processor.randomdata;

import com.mbzshajib.mining.Initializer;
import com.mbzshajib.mining.processor.randomdata.generator.RandomGeneratorInput;
import com.mbzshajib.mining.processor.randomdata.generator.RandomGeneratorOutput;
import com.mbzshajib.mining.processor.randomdata.generator.RandomGeneratorUtils;
import com.mbzshajib.mining.util.Configurations;

import java.io.IOException;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/13/2015
 * time: 9:05 PM
 * ****************************************************************
 */

public class MainRandomGenerator {
    public static void main(String[] args) throws IOException {
        Initializer.initialize();
        simulateWithStaticData();
        simulateDataFromFile();
    }

    private static void simulateWithStaticData() {
        RandomGeneratorInput input = RandomGeneratorUtils.getStaticInput();
        RandomGeneratorOutput output = RandomGeneratorUtils.simulate(input);
        RandomGeneratorUtils.print(input, output);
    }

    private static void simulateDataFromFile() throws IOException {
        Configurations configurations = Initializer.getConfigurations();
        RandomGeneratorInput input = RandomGeneratorUtils.getFileInput("INPUT/" + configurations.getRandomDataSetGeneratorInpuFile());
        RandomGeneratorOutput output = RandomGeneratorUtils.simulate(input);
        RandomGeneratorUtils.print(input, output);
    }

}
