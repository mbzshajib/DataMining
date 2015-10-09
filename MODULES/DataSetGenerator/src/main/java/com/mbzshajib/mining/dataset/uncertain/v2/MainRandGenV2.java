package com.mbzshajib.mining.dataset.uncertain.v2;


import com.mbzshajib.mining.dataset.uncertain.util.Constant;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.common.datasaver.DoubleDataSaverOutput;
import com.mbzshajib.utility.common.datasaver.DoubleDataToFileSaver;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.File;
import java.io.IOException;

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


public class MainRandGenV2 {
    public static final String CONFIGURATION_INPUT_FILE = Constants.DIR_INPUT + Constant.DIR_RANDOM_GEN + "rand_conf_v2.json";
    public static final String CONFIGURATION_INPUT_FILE_UNCERTAIN = Constants.DIR_INPUT + Constant.DIR_DATA_GEN + "conf_uncert_data_gen.json";

    public static void main(String[] args) throws IOException, ProcessingError {
        ConfigurationLoader<RandomGeneratorInputV2> loader = new ConfigurationLoader<RandomGeneratorInputV2>(RandomGeneratorInputV2.class);
        RandomGeneratorInputV2 generatorInput = loader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE));
        ConfigurationLoader<UDataSetGeneratorInput> dataSetGeneratorInputConfigurationLoader = new ConfigurationLoader<UDataSetGeneratorInput>(UDataSetGeneratorInput.class);
        UDataSetGeneratorInput uDataSetGeneratorInput = dataSetGeneratorInputConfigurationLoader.loadConfigDataFromJsonFile(new File(CONFIGURATION_INPUT_FILE_UNCERTAIN));
        int numberOfTransaction = FileUtility.countTransaction(uDataSetGeneratorInput.getPathCertData(), uDataSetGeneratorInput.getFileNameCertData(), uDataSetGeneratorInput.getTransactionDelemeter());
        generatorInput.setNumberOfRandomToBeGenerated(numberOfTransaction);
        generatorInput.setDataSaver(new DoubleDataToFileSaver());
        RandomGeneratorProcessor processor = new RandomGeneratorProcessor();
        RandomGeneratorOutputV2 output = processor.process(generatorInput);

        System.out.println(output.toString());
        DoubleDataSaverOutput saverOutput = (DoubleDataSaverOutput) output.getSaverOutput();
        uDataSetGeneratorInput.setFileNameUncertainity(saverOutput.getNameOfFile());
        uDataSetGeneratorInput.setPathUncertainity(saverOutput.getPathOfSavedDoubles());
        if (uDataSetGeneratorInput.getFileNameUncertData().equals("")) {
            uDataSetGeneratorInput.setFileNameUncertData("uncert_" + uDataSetGeneratorInput.getFileNameCertData());
        }
        UDataSetGenerator dataSetGenerator = new UDataSetGenerator();
        UDataSetGeneratorOutput process = dataSetGenerator.process(uDataSetGeneratorInput);

    }


}
