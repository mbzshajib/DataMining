package com.mbzshajib.utility;

import com.mbzshajib.utility.random.RandomUtility;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/4/2015
 * time: 6:27 PM
 * ****************************************************************
 */


public class Main {
    public static void main(String[] args) {
//        checkRandomGenerationInRange();
    }

    private static void checkRandomGenerationInRange() {
        double[] result = RandomUtility.getRandoms(.2, .22, 5, (byte) 30);
        for(double tmp : result){
            System.out.println(tmp);
        }
    }
}
