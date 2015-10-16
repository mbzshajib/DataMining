package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.util.Constants;
import com.mbzshajib.utility.configloader.ConfigurationLoader;
import com.mbzshajib.utility.file.Utility;
import com.mbzshajib.utility.log.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/14/2015
 * @time: 11:59 PM
 * ****************************************************************
 */

public class MiningInputGenerator {
    private static final String MINING_INPUT_FILE = Constants.F_MINING_PATH + Constants.F_MINING_FILE;

    private static final MiningInput loadMiningInputData() throws IOException {
        ConfigurationLoader<MiningInput> configurationLoader = new ConfigurationLoader<>(MiningInput.class);
        MiningInput miningInput = configurationLoader.loadConfigDataFromJsonFile(new File(MINING_INPUT_FILE));
        return miningInput;
    }

    public static List<MiningInput> generateMiningInputForMashroomDataSet() throws IOException {
        Logger.log("generating input data for mining mashroom data set.");
        List<MiningInput> miningInputList = new ArrayList<MiningInput>();
        ConfigurationLoader<MiningInputGeneratorConfig> loader = new ConfigurationLoader<MiningInputGeneratorConfig>(MiningInputGeneratorConfig.class);
        MiningInputGeneratorConfig config = loader.loadConfigDataFromJsonFile("Input/DataMining/", "input_for_mushroom_simulation.json");

        File metaDataPath = new File(config.getMetaDataFileDir());
        if (!metaDataPath.exists()) {
            metaDataPath.mkdir();
        }
        for (int frameNo = config.getFrameStart(); frameNo < config.getFrameEnd(); frameNo += config.getFrameInterval()) {
            for (int windowNo = config.getWindowStart(); windowNo < config.getWindowEnd(); windowNo += config.getWindowInterval()) {
                for (double support = config.getMinSupStart(); support < config.getMinSupEnd(); support += config.getMinSupInterval()) {
                    MiningInput miningInput = new MiningInput(new File("Generating Mining Input"));
                    miningInput.setDataSetName(config.getDataSetName());
                    miningInput.setDataSetPath(config.getDataSetDir());
                    miningInput.setMetaDataFile(config.getMetaDataFileName());
                    String dateStringSuffix = Utility.getDateTimeString();
                    String simulationIdSuffix = "f_" + frameNo + "_w_" + windowNo + "_minsup_" + support;
                    miningInput.setMetaDataPath(config.getMetaDataFileDir() + "" + dateStringSuffix + "/" + simulationIdSuffix + "/");
                    double minSup = support * windowNo * frameNo / 100;
                    miningInput.setMinSupport(minSup);
                    miningInput.setWindowSize(windowNo);
                    miningInput.setFrameSize(frameNo);
                    miningInput.setFindFalseNegative(config.isFindFalseNegative());
                    miningInputList.add(miningInput);
                }
            }
        }
        Logger.log("Mining input data generated.");
        return miningInputList;
    }


    public static void main(String[] args) throws IOException {
        generateMiningInputForMashroomDataSet();
    }
}
