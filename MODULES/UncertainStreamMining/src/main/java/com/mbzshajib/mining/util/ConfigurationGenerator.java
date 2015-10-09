package com.mbzshajib.mining.util;

import com.mbzshajib.mining.processor.uncertain.MiningInput;
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

    public static void main(String[] args) throws IOException {
        MiningInput miningInput = new MiningInput(new File(Constants.F_MINING_PATH + Constants.F_MINING_FILE));
        miningInput.setDataSetPath("data set path");
        miningInput.setDataSetName("data set name");
        miningInput.setFrameSize(100);
        miningInput.setWindowSize(100);
        miningInput.setFrequentSetPath("Frequent Item Set path");
        miningInput.setFrequentSetName("Frequent Item Set");
        ConfigurationLoader<MiningInput> miningInputConfigLoader = new ConfigurationLoader<>(MiningInput.class);
        miningInputConfigLoader.generateJsonConfigFile(Constants.F_MINING_PATH, Constants.F_MINING_FILE, miningInput);
    }
}
