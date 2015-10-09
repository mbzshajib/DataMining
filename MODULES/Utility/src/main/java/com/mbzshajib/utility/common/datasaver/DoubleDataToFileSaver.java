package com.mbzshajib.utility.common.datasaver;

import com.mbzshajib.utility.callbacks.DataSaver;
import com.mbzshajib.utility.callbacks.SaverInput;
import com.mbzshajib.utility.callbacks.SaverOutput;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:25 AM
 * ****************************************************************
 */


public class DoubleDataToFileSaver implements DataSaver {
    @Override
    public SaverInput prepareSaverInput(Object... input) {
        if (input.length != 3) {
            throw new IllegalArgumentException("Input parameters must be at least 3");
        }
        if (input[0] == null || !(input[0] instanceof String)) {
            throw new IllegalArgumentException("Input[0] must be of type String");
        }
        String path = (String) input[0];
        if (input[1] == null || !(input[1] instanceof String)) {
            throw new IllegalArgumentException("Input[1] must be of type String");
        }
        String fileName = (String) input[1];
        List<Double> doubles=null;
        try {
            doubles = (List<Double>) input[2];
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Input[2] must be of type List<Double>");
        }

        DoubleDataSaverInput result = new DoubleDataSaverInput(path, fileName, doubles);
        return result;
    }

    @Override
    public SaverOutput save(SaverInput input) throws ProcessingError {
        if (!(input instanceof DoubleDataSaverInput)) {
            throw new IllegalArgumentException("Input must by type of " + DoubleDataSaverInput.class.getName());
        }
        DoubleDataSaverInput doubleDataToFileSaver = (DoubleDataSaverInput) input;
        String path = doubleDataToFileSaver.getPathToBeSaved();
        String fileName = doubleDataToFileSaver.getFileName();
        if (path == null) {
            path = "";
        }
        if (fileName == null || fileName.isEmpty()) {
            fileName = System.currentTimeMillis() + Constants.FILE_EXT_DAT;
        }
        File filePath = new File(path);
        if (!(filePath.exists())) {
            filePath.mkdir();
        }
        String fullPathString = path + fileName;
        List<Double> doubles = doubleDataToFileSaver.getDoubles();
        Double[] dblArray = new Double[doubles.size()];
        doubles.toArray(dblArray);
        try {
            FileUtility.writeDoublesToFile(fullPathString, dblArray);
        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        DoubleDataSaverOutput output = new DoubleDataSaverOutput(path, fileName, dblArray.length);
        return output;
    }
}
