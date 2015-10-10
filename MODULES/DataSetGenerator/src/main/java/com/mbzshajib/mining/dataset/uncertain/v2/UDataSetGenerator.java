package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.file.FileUtility;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:16 AM
 * ****************************************************************
 */


public class UDataSetGenerator implements Processor<UDataSetGeneratorInput, UDataSetGeneratorOutput> {

    @Override
    public UDataSetGeneratorOutput process(UDataSetGeneratorInput uDataSetGeneratorInput) throws ProcessingError {
        List<Double> randomValues = null;
        int totalTransaction;
        try {
            randomValues = FileUtility.readDoublesFromFileAsList(uDataSetGeneratorInput.getPathUncertainity() + uDataSetGeneratorInput.getFileNameUncertainity());
            Collections.shuffle(randomValues);
            totalTransaction = FileUtility.countTransaction(uDataSetGeneratorInput.getPathCertData(), uDataSetGeneratorInput.getFileNameCertData(), uDataSetGeneratorInput.getTransactionDelemeter());
            if (totalTransaction != randomValues.size()) {
                throw new ProcessingError(new DataNotFoundException("Total Randoms " + randomValues.size() + " Total Transactions " + totalTransaction));
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(uDataSetGeneratorInput.getPathUncertData() + uDataSetGeneratorInput.getFileNameUncertData())));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(uDataSetGeneratorInput.getPathCertData() + uDataSetGeneratorInput.getFileNameCertData())));
            String readLine = null;
            int randomIndex = 0;
            while ((readLine = bufferedReader.readLine()) != null) {
                String[] split = readLine.split(uDataSetGeneratorInput.getTransactionDelemeter());
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    builder.append(split[i])
                            .append(uDataSetGeneratorInput.getUncertainityDelemeter())
                            .append(randomValues.get(randomIndex++))
                            .append(" ");
                }
                String uCertTransaction = builder.toString();
                uCertTransaction = uCertTransaction.substring(0, uCertTransaction.length() - 1) + "\n";
                bufferedWriter.write(uCertTransaction);
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new ProcessingError(e);
        }
        UDataSetGeneratorOutput output = new UDataSetGeneratorOutput();
        output.setPathDataSetGenerated(uDataSetGeneratorInput.getPathUncertData());
        output.setNameOfDataSet(uDataSetGeneratorInput.getFileNameUncertData());
        output.setTotalTransaction(totalTransaction);
        return output;

    }
}