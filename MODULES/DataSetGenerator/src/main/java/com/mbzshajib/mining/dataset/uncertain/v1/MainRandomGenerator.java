//package com.mbzshajib.mining.dataset.uncertain.v1;
//
//import com.mbzshajib.mining.dataset.uncertain.util.RandomGeneratorUtils;
//import com.mbzshajib.mining.dataset.uncertain.util.Constant;
//import com.mbzshajib.utility.file.FileUtility;
//
//import java.io.IOException;
//import java.util.Arrays;
//
///**
// * *****************************************************************
// * Copyright  2015.
// *
// * @author - Md. Badi-Uz-Zaman Shajib
// * @email - mbzshajib@gmail.com
// * @gitHub - https://github.com/mbzshajib
// * @date: 9/13/2015
// * @time: 9:05 PM
// * ****************************************************************
// */
//
//public class MainRandomGenerator {
//    private static final String OUTPUT_FILE_NAME = "output.txt";
//
//    public static void main(String[] args) throws IOException {
//        Initializer.initialize();
//        simulateWithStaticData();
//        simulateDataFromFile();
//    }
//
//    private static void simulateWithStaticData() {
//        RandomGeneratorInput input = RandomGeneratorUtils.getStaticInput();
//        RandomGeneratorOutput output = RandomGeneratorUtils.simulate(input);
//        RandomGeneratorUtils.print(input, output);
//    }
//
//    private static void simulateDataFromFile() throws IOException {
//        Configurations configurations = Initializer.getConfigurations();
//        RandomGeneratorInput input = RandomGeneratorUtils.getFileInput(Constant.DIR_INPUT + "");
//        RandomGeneratorOutput output = RandomGeneratorUtils.simulate(input);
//        RandomGeneratorUtils.print(input, output);
//        FileUtility.writeDoublesToFile(Constant.DIR_OUTPUT + OUTPUT_FILE_NAME, output.getAllValues());
//        double[] tmp = FileUtility.readDoublesFromFile(Constant.DIR_OUTPUT + OUTPUT_FILE_NAME);
//        System.out.println(Arrays.equals(tmp, output.getAllValues()));
//
//
//    }
//
//
//}
