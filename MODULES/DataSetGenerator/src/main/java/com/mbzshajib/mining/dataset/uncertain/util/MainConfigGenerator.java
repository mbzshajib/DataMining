package com.mbzshajib.mining.dataset.uncertain.util;

import com.mbzshajib.mining.dataset.uncertain.v2.RInputInfo;
import com.mbzshajib.mining.dataset.uncertain.v2.RandomGeneratorInputV2;
import com.mbzshajib.mining.dataset.uncertain.uncertaindatageneration.UDataSetGeneratorInput;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/8/2015
 * @time: 4:28 PM
 * ****************************************************************
 */


public class MainConfigGenerator {
    public static final String FN_RAND_GEN_INPUT_V2 = Constants.DIR_OUTPUT + "rand_conf_v2.json";
    public static final String FN_UNCRT_GEN_INPUT = Constants.DIR_OUTPUT + "conf_uncert_data_gen.json";

    public static void main(String[] args) throws IOException {
//        generateInputForRandGeneratorV2();
        generateConfigForUncertainData();
    }

    private static void generateConfigForUncertainData() throws IOException {
        UDataSetGeneratorInput input = new UDataSetGeneratorInput(new File(FN_UNCRT_GEN_INPUT));
        input.setFileNameCertData("");
        input.setFileNameUncertainity("");
        input.setFileNameUncertData("");
        input.setPathCertData("");
        input.setPathUncertData("");
        input.setPathUncertainity("");
        ConfigurationLoader<UDataSetGeneratorInput> loader = new ConfigurationLoader<UDataSetGeneratorInput>(UDataSetGeneratorInput.class);
        loader.generateJsonConfigFile(Constants.DIR_OUTPUT, "conf_uncert_data_gen.json", input);

    }

    private static void generateInputForRandGeneratorV2() throws IOException {
        generateConfigFileForInputOfRandamGeneratorV2();
    }

    private static void generateConfigFileForInputOfRandamGeneratorV2() throws IOException {
        RandomGeneratorInputV2 randomGeneratorInputV2 = new RandomGeneratorInputV2(new File(FN_RAND_GEN_INPUT_V2));
        List<RInputInfo> list = new ArrayList<RInputInfo>();
        randomGeneratorInputV2.setNumberOfRandomToBeGenerated(10000);
        randomGeneratorInputV2.setOutputPath("");
        randomGeneratorInputV2.setOutputFileName("");
        for (int i = 0; i < 10; i++) {
            RInputInfo rInputInfo = new RInputInfo(Double.parseDouble("." + (i * 10)), Double.parseDouble("." + (i + 1) * 10), 10);
            rInputInfo.setPrecision((byte) 2);
            list.add(rInputInfo);
        }
        randomGeneratorInputV2.setList(list);
        ConfigurationLoader<RandomGeneratorInputV2> generatorRandGenV2 = new ConfigurationLoader<RandomGeneratorInputV2>(RandomGeneratorInputV2.class);
        generatorRandGenV2.generateJsonConfigFile(Constants.DIR_OUTPUT, "rand_conf_v2.json", randomGeneratorInputV2);
        RandomGeneratorInputV2 result = generatorRandGenV2.loadConfigDataFromJsonFile(new File(FN_RAND_GEN_INPUT_V2));
        System.out.println(result.toString());
    }
}
