package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.callbacks.SaverInput;
import com.mbzshajib.utility.callbacks.SaverOutput;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;
import com.mbzshajib.utility.random.RandomUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 10:09 AM
 * ****************************************************************
 */


public class RandomGeneratorProcessor implements Processor<RandomGeneratorInputV2, RandomGeneratorOutputV2> {
    @Override
    public RandomGeneratorOutputV2 process(RandomGeneratorInputV2 input) throws ProcessingError {
        List<Double> result = new ArrayList<Double>();
        updateWeight(input.getList(), input.getNumberOfRandomToBeGenerated());
        for (RInputInfo info : input.getList()) {
            double[] randoms = RandomUtility.getRandoms(info.getLowerValue(), info.getUpperValue(), info.getWeight(), info.getPrecision());
            for (double tmp : randoms) {
                result.add(tmp);
            }
        }
        RandomGeneratorOutputV2 output = new RandomGeneratorOutputV2();
        output.setRandoms(result);

        SaverInput saverInput = input.getDataSaver().prepareSaverInput(input.getOutputPath(), input.getOutputFileName(), result);
        SaverOutput saverOutput = input.getDataSaver().save(saverInput);
        output.setSaverOutput(saverOutput);
        return output;
    }

    private void updateWeight(List<RInputInfo> list, int totalNumberOfRandom) {
        int totalWeight = 0;
        for (RInputInfo rInputInfo : list) {
            totalWeight += rInputInfo.getWeight();
        }
        int newTotalWeight = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            RInputInfo info = list.get(i);
            int calculatedWeight = (totalNumberOfRandom / totalWeight) * info.getWeight();
            info.setWeight(calculatedWeight);
            newTotalWeight = newTotalWeight + calculatedWeight;
        }
        list.get(list.size() - 1).setWeight(totalNumberOfRandom - newTotalWeight);
        int i = 0;
        for (RInputInfo info : list) {
            i = i + info.getWeight();
        }
    }
}