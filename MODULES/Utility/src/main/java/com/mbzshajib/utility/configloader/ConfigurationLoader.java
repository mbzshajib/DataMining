package com.mbzshajib.utility.configloader;

import com.mbzshajib.utility.exception.DataNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
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


public class ConfigurationLoader {

    public static void loadConfigDataFromPropertiesFile(PropConfigData propConfigData) throws IOException, IllegalAccessException, DataNotFoundException {
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
}
