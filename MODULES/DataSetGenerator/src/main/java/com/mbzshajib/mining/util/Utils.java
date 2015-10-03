package com.mbzshajib.mining.util;

import com.mbzshajib.mining.Initializer;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 4:39 PM
 * ****************************************************************
 */


public class Utils {
    private static String mode = "DEBUG";

    public static void log(String tag, String message) {
        Configurations configurations = Initializer.getConfigurations();
        String programMode = configurations.getRunningMode();
        if (programMode.equals(mode)) {
            System.out.println(tag + "\t\t->\t\t" + message);
        }
    }

    public static void log(String message) {
        Configurations configurations = Initializer.getConfigurations();
        String programMode = configurations.getRunningMode();
        if (programMode.equals(mode)) {
            System.out.println(message);
        }
    }

}
