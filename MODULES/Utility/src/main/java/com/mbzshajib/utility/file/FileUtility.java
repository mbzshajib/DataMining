package com.mbzshajib.utility.file;

import java.io.*;

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

    public static void createUncertainDataSetAndWrite(String certainDataSetLocation, String probabilityLocation, String outputLocation) throws IOException {
        File certDataFile = new File(certainDataSetLocation);
        FileReader fileReader = new FileReader(certDataFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
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
            dir.mkdir();
        }
    }
}
