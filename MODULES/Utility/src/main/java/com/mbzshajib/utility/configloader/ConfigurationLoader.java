package com.mbzshajib.utility.configloader;

import com.google.gson.Gson;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.file.FileUtility;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/6/2015
 * @time: 11:33 AM
 * ****************************************************************
 */


public class ConfigurationLoader<E extends ConfigModel> {
    final Class<E> typeParameterClass;

    public ConfigurationLoader(Class<E> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void loadConfigDataFromPropertiesFile(PropConfigData propConfigData) throws IOException, IllegalAccessException, DataNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(propConfigData.getFile());
        Properties properties = new Properties();
        properties.load(fileInputStream);
        Field[] fields = propConfigData.getClass().getFields();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if (Modifier.isPublic(modifier) && Modifier.isStatic(modifier) && Modifier.isFinal(modifier) && field.getType() == String.class) {
                Object obj = new Object();
                String key = field.get(obj).toString();
                String value = properties.getProperty(key);
                if (value == null) {
                    throw new DataNotFoundException("Properties data not found for key " + key);
                }
                propConfigData.setProperty(key, value);

            }
        }


    }

    public E loadConfigDataFromJsonFile(File file) throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
        E e = (E) gson.fromJson(jsonString, this.typeParameterClass);
        return e;
    }

    public void generateJsonConfigFile(String path, String fileName, E e) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(e);
        FileUtility.writeFile(path, fileName + Constants.FILE_EXT_JSON, jsonString);
    }
}
