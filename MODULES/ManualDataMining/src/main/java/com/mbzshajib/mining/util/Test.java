package com.mbzshajib.mining.util;

import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.utility.collection.PowerSetGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 8:05 PM
 * ****************************************************************
 */

public class Test {
    public static void main(String[] args) {
        PowerSetGenerator<UInputData> powerSetGenerator = new PowerSetGenerator<UInputData>();
        List<UInputData> list = new ArrayList<UInputData>();

        UInputData UInputData = new UInputData("1", .2);
        UInputData UInputData1 = new UInputData("2", .2);
        UInputData UInputData2 = new UInputData("3", .3);
        list.add(UInputData);
        list.add(UInputData1);
        list.add(UInputData2);

        List<List<UInputData>> result = powerSetGenerator.generatePowerSet(list);
        for (List<UInputData> tmp : result) {
            for (UInputData s : tmp) {
                System.out.print(s.toString() + " ");
            }
            System.out.println();
        }
        System.out.println("Total Set " + result.size());
    }
}
