package com.mbzshajib.utility.log;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:52 PM
 * ****************************************************************
 */

public class Logger {
    private static String mode = "DEBUG";
    private static String DEBUG = "DEBUG";
    private static String RUN = "DEBUG";

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
