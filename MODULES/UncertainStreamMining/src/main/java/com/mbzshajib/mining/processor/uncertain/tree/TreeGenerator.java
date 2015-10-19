package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.mining.processor.uncertain.model.UInputData;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:03 PM
 * ****************************************************************
 */

public class TreeGenerator implements Processor<TreeConstructionInput, TreeConstructionOutput> {
    private BufferedReader bufferedReader;
    private long timePointer;
    private long fileReadTimeNeeded;

    @Override
    public TreeConstructionOutput process(TreeConstructionInput treeConstructionInput) throws ProcessingError {
        this.bufferedReader = treeConstructionInput.getBufferedReader();
        this.timePointer = System.currentTimeMillis();
        this.fileReadTimeNeeded = 0;
        UncertainTree uncertainTree = null;
        try {
            uncertainTree = new UncertainTree(treeConstructionInput.getFrameSize(), treeConstructionInput.getWindowSize());
            for (int frameNo = 0; frameNo < treeConstructionInput.getWindowSize(); frameNo++) {
                for (int i = 0; i < treeConstructionInput.getFrameSize(); i++) {
                    List<UInputData> nodes = getTransaction();
                    uncertainTree.addTransactionToTree(nodes, frameNo);
                }
            }
            treeConstructionInput.getWindowCompletionCallback().sendUpdate(createWindowOutput(uncertainTree));
            uncertainTree.slideWindowAndUpdateTree();
            timePointer = System.currentTimeMillis();
            List<UInputData> nodes = null;
            int frameCounter = 0;
            while (!(nodes = getTransaction()).isEmpty()) {
                if (!(frameCounter < treeConstructionInput.getFrameSize())) {
                    frameCounter = 0;
                    treeConstructionInput.getWindowCompletionCallback().sendUpdate(createWindowOutput(uncertainTree));
                    uncertainTree.slideWindowAndUpdateTree();
                    timePointer = System.currentTimeMillis();
                }
                uncertainTree.addTransactionToTree(nodes, treeConstructionInput.getWindowSize() - 1);
                frameCounter++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createWindowOutput(uncertainTree);
    }

    private TreeConstructionOutput createWindowOutput(UncertainTree uncertainTree) {
        TreeConstructionOutput treeConstructionOutput = new TreeConstructionOutput();
        TimeModel tTreeConstruction = new TimeModel(this.timePointer, System.currentTimeMillis());
        TimeModel tFileRead = new TimeModel(this.fileReadTimeNeeded);
        this.timePointer = System.currentTimeMillis();
        this.fileReadTimeNeeded = 0;
        treeConstructionOutput.setTreeConstructionTime(tTreeConstruction);
        treeConstructionOutput.setScanningTransactionTime(tFileRead);
        try {
            treeConstructionOutput.setUncertainTree(uncertainTree.copy());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return treeConstructionOutput;
    }

    private List<UInputData> getTransaction() throws IOException, DataNotFoundException {
        long fileReadSTime = System.currentTimeMillis();
        String line = bufferedReader.readLine();
        if (line == null) {
            bufferedReader.close();
            return Collections.emptyList();
        }

        List<UInputData> uNodeList = new ArrayList<UInputData>();

        String[] transactionItems = line.split(" ");
        String id = transactionItems[0].split("-")[0];
        double probability = Double.parseDouble(transactionItems[0].split("-")[1]);
        UInputData firstNode = new UInputData(id, probability);
        double maxProbability = firstNode.getItemPValue();
        firstNode.setPrefixValue(maxProbability);
        uNodeList.add(firstNode);
        for (int i = 1; i < transactionItems.length; i++) {
            String tmpId = transactionItems[i].split("-")[0];
            double tmpPValue = Double.parseDouble(transactionItems[i].split("-")[1]);
            UInputData data = new UInputData(tmpId, tmpPValue);
            double prefixValueToBeAssigned = maxProbability * data.getItemPValue();
            data.setPrefixValue(prefixValueToBeAssigned);
            uNodeList.add(data);
            if (maxProbability < data.getItemPValue()) {
                maxProbability = data.getItemPValue();
            }
        }
        long fileReadETime = System.currentTimeMillis();
        long timeNeeded = fileReadETime - fileReadSTime;
        this.fileReadTimeNeeded = this.fileReadTimeNeeded + timeNeeded;
        return uNodeList;
    }
}
