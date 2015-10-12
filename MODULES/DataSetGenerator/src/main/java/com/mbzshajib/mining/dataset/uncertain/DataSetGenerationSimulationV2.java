package com.mbzshajib.mining.dataset.uncertain;


import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGenerator;
import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGeneratorInput;
import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGeneratorOutput;
import com.mbzshajib.mining.dataset.uncertain.util.Constant;
import com.mbzshajib.mining.dataset.uncertain.v2.*;
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
 * @date: 10/6/2015
 * @time: 11:25 AM
 * ****************************************************************
 */


public class DataSetGenerationSimulationV2 {
    public static final String CONFIGURATION_INPUT_FILE = Constants.DIR_INPUT + Constant.DIR_RANDOM_GEN + "rand_conf_v2.json";
    public static final String CONFIGURATION_INPUT_FILE_UNCERTAIN = Constants.DIR_INPUT + Constant.DIR_DATA_GEN + "conf_uncert_data_gen.json";

    public static void main(String[] args) throws IOException, ProcessingError {
        long startTime = System.currentTimeMillis();
        Logger.log("Data Set Generation Started.");

        ConfigurationLoader<UDataSetGeneratorInput> dataGenConfigLoader = new ConfigurationLoader<UDataSetGeneratorInput>(UDataSetGeneratorInput.class);
        UDataSetGeneratorInput uDataSetGeneratorInput = dataGenConfigLoader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE_UNCERTAIN));

        ConfigurationLoader<RandomGeneratorInputV2> randGenConfigLoader = new ConfigurationLoader<RandomGeneratorInputV2>(RandomGeneratorInputV2.class);
        RandomGeneratorInputV2 generatorInput = randGenConfigLoader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE));

        int numberOfTransaction = FileUtility.countTransaction(uDataSetGeneratorInput.getPathCertData(), uDataSetGeneratorInput.getFileNameCertData(), uDataSetGeneratorInput.getTransactionDelemeter());
        generatorInput.setNumberOfRandomToBeGenerated(numberOfTransaction);
        generatorInput.setDataSaver(new DoubleDataToFileSaver());

        Logger.log("Random ", "Generating Random With Configuration " + generatorInput.toString());
        RandomGeneratorV2 processor = new RandomGeneratorV2();
        RandomGeneratorOutputV2 randomGenOutput = processor.process(generatorInput);
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


}
