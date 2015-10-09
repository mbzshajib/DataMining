package com.mbzshajib.mining;

import com.mbzshajib.mining.util.Configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:41 PM
 * ****************************************************************
 */


public class Initializer {
    private static Configurations configuration;

    public static void initialize() throws IOException {
        loadProperties();
    }

    private static void loadProperties() throws IOException {
//        URL defaultPropertiesLocation = ClassLoader.getSystemClassLoader().getResource(Configurations.F_DATA_SET_GENERATOR_PROPERTIES);
        URL defaultPropertiesLocation = ClassLoader.getSystemResource(Configurations.F_DATA_SET_GENERATOR_PROPERTIES);


        FileInputStream fileInputStream = new FileInputStream(defaultPropertiesLocation.getFile());
        Properties properties = new Properties();
        configuration = new Configurations();
        properties.load(fileInputStream);
        String authorName = properties.getProperty(Configurations.K_AUTHOR_NAME);
        String runningMode = properties.getProperty(Configurations.K_RUNNING_MODE);
        String randomDataSetGeneratorPropertiesFileName = properties.getProperty(Configurations.K_RANDOM_DATA_GENERATOR);
        configuration.setAuthorName(authorName);
        configuration.setRunningMode(runningMode);
        configuration.setRandomDataSetGeneratorInpuFile(randomDataSetGeneratorPropertiesFileName);
    }

    public static Configurations getConfigurations() {
        if (configuration == null) {
            throw new NullPointerException("Configuration is null. could not configure properly.");
        }
        return configuration;
    }
}
