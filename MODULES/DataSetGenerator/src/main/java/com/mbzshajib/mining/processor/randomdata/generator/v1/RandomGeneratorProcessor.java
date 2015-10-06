package com.mbzshajib.mining.processor.randomdata.generator.v1;

import com.mbzshajib.utility.model.Processor;
import com.mbzshajib.utility.random.RandomUtility;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/13/2015
 * @time: 7:57 PM
 * ****************************************************************
 */

public class RandomGeneratorProcessor implements Processor<RandomGeneratorInput, RandomGeneratorOutput> {

    @Override
    public RandomGeneratorOutput process(RandomGeneratorInput randomSetGeneratorInput) {

        int lowerPeakDataSize = (randomSetGeneratorInput.getTotalInput() * (100 - randomSetGeneratorInput.getPeakValuePercentage())) / 200;
        int upperPeakDataSize = randomSetGeneratorInput.getTotalInput() - 2 * lowerPeakDataSize;

        double[] lowerValues = RandomUtility.getRandoms(randomSetGeneratorInput.getMinRandValue(), randomSetGeneratorInput.getLowerPeakValue(), lowerPeakDataSize, randomSetGeneratorInput.getPrecision());
        double[] peakValues = RandomUtility.getRandoms(randomSetGeneratorInput.getLowerPeakValue(), randomSetGeneratorInput.getUpperPeakValue(), upperPeakDataSize, randomSetGeneratorInput.getPrecision());
        double[] upperValues = RandomUtility.getRandoms(randomSetGeneratorInput.getUpperPeakValue(), randomSetGeneratorInput.getMaxRandValue(), lowerPeakDataSize, randomSetGeneratorInput.getPrecision());

        RandomGeneratorOutput randomSetGeneratorOutput = new RandomGeneratorOutput();
        randomSetGeneratorOutput.setLowerValues(lowerValues);
        randomSetGeneratorOutput.setPeakValues(peakValues);
        randomSetGeneratorOutput.setUpperValues(upperValues);

        return randomSetGeneratorOutput;
    }
}
