package com.mbzshajib.mining;

import com.mbzshajib.mining.util.Configurations;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:50 PM
 * ****************************************************************
 */
     

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Initializer.initialize();
        Configurations configurations = Initializer.getConfigurations();
        System.out.println(configurations.getAuthorName());
        System.out.println(configurations.getRunningMode());
        System.out.println(configurations.getRandomDataSetGeneratorInpuFile());
    }
}
