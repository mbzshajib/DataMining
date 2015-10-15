package com.mbzshajib.utility.file;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/15/2015
 * @time: 10:21 PM
 * ****************************************************************
 */

public class Utility {
    public static String getDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss");
        return com.mbzshajib.utility.common.Constants.UNDER_SCORE + sdf.format(new Date());
    }
}
