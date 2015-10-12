package com.mbzshajib.mining.dataset.uncertain.v3;

import com.mbzshajib.utility.callbacks.SaverInput;
import com.mbzshajib.utility.callbacks.SaverOutput;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;
import com.mbzshajib.utility.random.GaussianRandom;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/12/2015
 * @time: 12:53 PM
 * ****************************************************************
 */


public class RandomGeneratorV3Gaussian implements Processor<RandomGeneratorInputV3, RandomGeneratorOutputV3> {
    @Override
    public RandomGeneratorOutputV3 process(RandomGeneratorInputV3 input) throws ProcessingError {
        int numberOfRandom = input.getNumberOfRandom();
        double variance = input.getVariance();
        double mean = input.getMean();
        GaussianRandom gaussianRandom = new GaussianRandom(mean, variance);
        List<Double> randomNumbers = gaussianRandom.getGaussianRandoms(numberOfRandom);
        RandomGeneratorOutputV3 output = new RandomGeneratorOutputV3();
        output.setRandoms(randomNumbers);
        SaverInput saverInput = input.getDataSaver().prepareSaverInput(input.getOutputPath(), input.getOutputFileName(), randomNumbers);
        SaverOutput saverOutput = input.getDataSaver().save(saverInput);
        output.setSaverOutput(saverOutput);
        return output;
    }
}
