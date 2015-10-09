package com.mbzshajib.mining.dataset.uncertain.util;

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
    private static String RUNNING_MODE = "DEBUG";
    private static String MODE_DEBUG = "DEBUG";
    private static String MODE_RELEASE = "RELEASE";

    public static void log(String tag, String message) {
        if (MODE_DEBUG.equals(RUNNING_MODE)) {
            System.out.println(tag + "\t\t->\t\t" + message);
        }
    }

    public static void log(String message) {
        if (MODE_DEBUG.equals(RUNNING_MODE)) {
            System.out.println(message);
        }
    }

}
