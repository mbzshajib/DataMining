package com.mbzshajib.utility.file;

import com.mbzshajib.utility.common.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 4:31 PM
 * ****************************************************************
 */


public class FileUtility {

    public static void writeDoublesToFile(String filePath, double[] values) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            file.createNewFile();
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        dataOutputStream.writeUTF("DATA_COUNT:");
        dataOutputStream.writeInt(values.length);
        for (double val : values) {
            dataOutputStream.writeDouble(val);
        }
        dataOutputStream.close();
    }

    public static void writeDoublesToFile(String filePath, Double[] values) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            file.createNewFile();
        }
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        dataOutputStream.writeUTF("DATA_COUNT:");
        dataOutputStream.writeInt(values.length);
        for (double val : values) {
            dataOutputStream.writeDouble(val);
        }
        dataOutputStream.close();
    }

    public static double[] readDoublesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        dataInputStream.readUTF();
        int totalDoubleValues = dataInputStream.readInt();
        double[] result = new double[totalDoubleValues];

        for (int i = 0; i < totalDoubleValues; i++) {
            result[i] = dataInputStream.readDouble();
        }
        dataInputStream.close();
        return result;
    }

    public static List<Double> readDoublesFromFileAsList(String filePath) throws IOException {
        File file = new File(filePath);
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        dataInputStream.readUTF();
        int totalDoubleValues = dataInputStream.readInt();
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < totalDoubleValues; i++) {
            result.add(dataInputStream.readDouble());

        }
        dataInputStream.close();
        return result;
    }

    public static void createUncertainDataSetAndWrite(String certainDataSetLocation, String probabilityLocation, String outputLocation) throws IOException {
        File certDataFile = new File(certainDataSetLocation);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(certDataFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputLocation));
        double[] probabilityValue = readDoublesFromFile(probabilityLocation);
        String line;
        int count = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokensizedItem = line.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < tokensizedItem.length; i++) {
                tokensizedItem[i] = tokensizedItem[i] + "-" + (probabilityValue[count++]);
                stringBuilder.append(tokensizedItem[i]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
            bufferedWriter.write(stringBuilder.toString());
        }
        bufferedReader.close();

    }

    public static void writeFile(String path, String fileName, String data) throws IOException {
        createPath(path);
        String fullPath = path + fileName;
        File file = new File(fullPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(data);
        bufferedWriter.close();
    }

    private static void createPath(String path) {
        File dir = new File(path);
        if (!(dir.exists())) {
            dir.mkdirs();
        }
    }

    public static int countTransaction(String pathCertData, String fileNameCertData, String transactionDelemeter) throws IOException {
        File file = new File(pathCertData + fileNameCertData);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        int totalCount = 0;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(transactionDelemeter);
            totalCount = totalCount + split.length;
        }
        reader.close();
        return totalCount;

    }

    public static void writeSingleLine(String resultDir, String resultFileName, String message) throws IOException {
        createPath(resultDir);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultDir + resultFileName, true));
        bufferedWriter.write(Utility.getDateTimeString() + Constants.TAB + message);
        bufferedWriter.close();


    }
}
