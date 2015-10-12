package com.mbzshajib.mining.dataset.uncertain;

import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGenerator;
import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGeneratorInput;
import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGeneratorOutput;
import com.mbzshajib.mining.dataset.uncertain.util.Constant;
import com.mbzshajib.mining.dataset.uncertain.v2.RandomGeneratorInputV2;
import com.mbzshajib.mining.dataset.uncertain.v3.RandomGeneratorInputV3;
import com.mbzshajib.mining.dataset.uncertain.v3.RandomGeneratorOutputV3;
import com.mbzshajib.mining.dataset.uncertain.v3.RandomGeneratorV3Gaussian;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.common.datasaver.DoubleDataSaverOutput;
import com.mbzshajib.utility.common.datasaver.DoubleDataToFileSaver;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/12/2015
 * @time: 12:57 PM
 * ****************************************************************
 */


public class DataSetGenerationSimulationGaussian {
    public static final String CONFIGURATION_INPUT_FILE = Constants.DIR_INPUT + Constant.DIR_RANDOM_GEN + "rand_conf_v2.json";
    public static final String CONFIGURATION_INPUT_FILE_UNCERTAIN = Constants.DIR_INPUT + Constant.DIR_DATA_GEN + "conf_uncert_data_gen.json";
    private static final double MEAN = .5;
    private static final double VARIANCE = MEAN / Math.PI;

    public static void main(String[] args) throws ProcessingError, IOException {
        long startTime = System.currentTimeMillis();
        Logger.log("Data Set Generation Started.");

        ConfigurationLoader<UDataSetGeneratorInput> dataGenConfigLoader = new ConfigurationLoader<UDataSetGeneratorInput>(UDataSetGeneratorInput.class);
        UDataSetGeneratorInput uDataSetGeneratorInput = dataGenConfigLoader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE_UNCERTAIN));

        ConfigurationLoader<RandomGeneratorInputV2> randGenConfigLoader = new ConfigurationLoader<RandomGeneratorInputV2>(RandomGeneratorInputV2.class);
        RandomGeneratorInputV2 generatorInput = randGenConfigLoader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE));

        int numberOfTransaction = FileUtility.countTransaction(uDataSetGeneratorInput.getPathCertData(), uDataSetGeneratorInput.getFileNameCertData(), uDataSetGeneratorInput.getTransactionDelemeter());
        generatorInput.setNumberOfRandomToBeGenerated(numberOfTransaction);
        RandomGeneratorInputV3 input = getInputForV3(generatorInput);
        Logger.log("Random ", "Generating Random With Configuration " + generatorInput.toString());
        RandomGeneratorV3Gaussian processor = new RandomGeneratorV3Gaussian();
        RandomGeneratorOutputV3 randomGenOutput = processor.process(input);
        DoubleDataSaverOutput saverOutput = (DoubleDataSaverOutput) randomGenOutput.getSaverOutput();
        Logger.log("Random ", "Random Generation Completed RandomGenOutput " + randomGenOutput.toString());

        uDataSetGeneratorInput.setFileNameUncertainity(saverOutput.getNameOfFile());
        uDataSetGeneratorInput.setPathUncertainity(saverOutput.getPathOfSavedDoubles());

        if (uDataSetGeneratorInput.getFileNameUncertData().equals("")) {
            uDataSetGeneratorInput.setFileNameUncertData("uncert_" + uDataSetGeneratorInput.getFileNameCertData());
        }
        Logger.log("Data Set ", "Data Set Generation started With Configuration " + uDataSetGeneratorInput.toString());
        UDataSetGenerator dataSetGenerator = new UDataSetGenerator();
        UDataSetGeneratorOutput dataSetGeneratorOutput = dataSetGenerator.process(uDataSetGeneratorInput);
        Logger.log("Data Set ", "Data Set Generation Completed DataSetGeneratorOutput " + dataSetGeneratorOutput.toString());

        long endTime = System.currentTimeMillis();
        Logger.log("Completed Data Set Generation.");
        Logger.log("Time Start = " + new Date(startTime).toString());
        Logger.log("Time End = " + new Date(endTime).toString());
        Logger.log("Time Needed= " + (endTime - startTime) + " ms");
    }

    private static RandomGeneratorInputV3 getInputForV3(RandomGeneratorInputV2 generatorInput) {
        RandomGeneratorInputV3 input = new RandomGeneratorInputV3();
        input.setDataSaver(new DoubleDataToFileSaver());
        input.setMean(MEAN);
        input.setVariance(VARIANCE);
        input.setNumberOfRandom(generatorInput.getNumberOfRandomToBeGenerated());
        input.setOutputPath(generatorInput.getOutputPath());
        input.setOutputFileName(generatorInput.getOutputFileName());
        return input;
    }


}
