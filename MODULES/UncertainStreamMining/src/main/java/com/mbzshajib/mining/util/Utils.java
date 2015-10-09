package com.mbzshajib.mining.util;

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
    private static String DEBUG= "DEBUG";
    private static String RUN= "DEBUG";

    public static void log(String tag, String message) {
        if (DEBUG.equals(mode)) {
            System.out.println(tag + "\t\t->\t\t" + message);
        }
    }

    public static void log(String message) {
        if (DEBUG.equals(mode)) {
            System.out.println(message);
        }
    }

}
