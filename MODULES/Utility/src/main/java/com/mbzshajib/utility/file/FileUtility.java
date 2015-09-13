package com.mbzshajib.utility.file;

import java.io.*;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/4/2015
 * time: 4:31 PM
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
}
