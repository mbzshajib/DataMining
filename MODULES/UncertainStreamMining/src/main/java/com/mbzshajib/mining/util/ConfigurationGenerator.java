package com.mbzshajib.mining.util;

import com.mbzshajib.mining.processor.uncertain.simulator.MiningInput;
import com.mbzshajib.mining.processor.uncertain.simulator.MiningInputGeneratorConfig;
import com.mbzshajib.utility.configloader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 6:01 PM
 * ****************************************************************
 */


public class ConfigurationGenerator {

    private static final String MINING_INPUT = Constants.F_MINING_PATH + Constants.F_MINING_FILE;
    private static final String MINING_INPUT_GEN = Constants.F_MINING_PATH + Constants.F_MINING_INPUT_GEN;

    public static void main(String[] args) throws IOException {
//        generateInputForMining();
        generateConfigJsonForInputGenerator();
    }

    private static void generateConfigJsonForInputGenerator() throws IOException {
        MiningInputGeneratorConfig config = new MiningInputGeneratorConfig(new File(MINING_INPUT_GEN));
        config.setMinSupStart(.1);
        config.setMinSupEnd(1);
        config.setMinSupInterval(.1);
        config.setFrameStart(100);
        config.setFrameEnd(1000);
        config.setFrameInterval(100);
        config.setWindowStart(3);
        config.setWindowEnd(6);
        config.setWindowInterval(1);
        config.setDataSetDir("Input/DataMining/");
        config.setDataSetName("mushroom_uncertain.dat");
        config.setMetaDataFileDir(Constants.DIR_META_USM + "mushroom");
        config.setMetaDataFileName("meta.json");
        ConfigurationLoader<MiningInputGeneratorConfig> loader = new ConfigurationLoader<MiningInputGeneratorConfig>(MiningInputGeneratorConfig.class);
        loader.generateJsonConfigFile("Input/DataMining/","mining.json",config);
    }

    private static void generateInputForMining() throws IOException {
        MiningInput miningInput = new MiningInput(new File(MINING_INPUT));
        miningInput.setDataSetPath("data set path");
        miningInput.setDataSetName("data set name");
        miningInput.setFrameSize(100);
        miningInput.setWindowSize(100);
        ConfigurationLoader<MiningInput> miningInputConfigLoader = new ConfigurationLoader<>(MiningInput.class);
        miningInputConfigLoader.generateJsonConfigFile(Constants.F_MINING_PATH, Constants.F_MINING_FILE, miningInput);
    }

}
