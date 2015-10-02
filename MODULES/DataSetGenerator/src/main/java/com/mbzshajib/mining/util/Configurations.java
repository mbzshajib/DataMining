package com.mbzshajib.mining.util;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:43 PM
 * ****************************************************************
 */


public class Configurations {
    public static final String F_DATA_SET_GENERATOR_PROPERTIES = "data_set_generator.properties";
    public static final String K_AUTHOR_NAME = "author_name";
    public static final String K_RUNNING_MODE = "running_mode";
    public static final String K_RANDOM_DATA_GENERATOR = "random_data_generator";

    private String authorName;
    private String runningMode;
    private String randomDataSetGeneratorInpuFile;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(String runningMode) {
        this.runningMode = runningMode;
    }

    public String getRandomDataSetGeneratorInpuFile() {
        return randomDataSetGeneratorInpuFile;
    }

    public void setRandomDataSetGeneratorInpuFile(String randomDataSetGeneratorInpuFile) {
        this.randomDataSetGeneratorInpuFile = randomDataSetGeneratorInpuFile;
    }
}
