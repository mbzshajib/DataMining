package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.processor.uncertain.model.SufInputData;
import com.mbzshajib.mining.processor.uncertain.model.SufTree;
import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.utility.exception.DataNotFoundException;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/20/2015
 * @time: 11:59 PM
 * ****************************************************************
 */

public class SufTreeGenerator implements Processor<SufTreeConstructionInput, SufTreeConstructorOutput> {
    private BufferedReader bufferedReader;
    private long timePointer;
    private long fileReadTimeNeeded;

    @Override
    public SufTreeConstructorOutput process(SufTreeConstructionInput treeConstructionInput) throws ProcessingError {
        this.bufferedReader = treeConstructionInput.getBufferedReader();
        this.timePointer = System.currentTimeMillis();
        this.fileReadTimeNeeded = 0;
        SufTree sufTree = null;
        try {
            sufTree = new SufTree(treeConstructionInput.getWindowSize(), treeConstructionInput.getFrameSize());
            for (int frameNo = 0; frameNo < treeConstructionInput.getWindowSize(); frameNo++) {
                for (int i = 0; i < treeConstructionInput.getFrameSize(); i++) {
                    List<SufInputData> nodes = getTransaction();
                    sufTree.addTransactionToTree(nodes, frameNo);
                }
            }
            treeConstructionInput.getSufCompleteCallback().sendUpdate(createWindowOutput(sufTree.copy()));
            sufTree.slideWindowAndUpdateTree();
            List<SufInputData> nodes = null;
            int frameCounter = 0;
            while (!(nodes = getTransaction()).isEmpty()) {
                if (!(frameCounter < treeConstructionInput.getFrameSize())) {
                    frameCounter = 0;
                    treeConstructionInput.getSufCompleteCallback().sendUpdate(createWindowOutput(sufTree.copy()));
                    sufTree.slideWindowAndUpdateTree();
                }
                sufTree.addTransactionToTree(nodes, treeConstructionInput.getWindowSize() - 1);
                frameCounter++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createWindowOutput(sufTree);
    }

    private SufTreeConstructorOutput createWindowOutput(SufTree sufTree) {
        SufTreeConstructorOutput treeConstructionOutput = new SufTreeConstructorOutput();
        treeConstructionOutput.setSufTree(sufTree);
        TimeModel tTreeConstruction = new TimeModel(this.timePointer, System.currentTimeMillis());
        TimeModel tFileRead = new TimeModel(this.fileReadTimeNeeded);
        this.timePointer = System.currentTimeMillis();
        this.fileReadTimeNeeded = 0;
        treeConstructionOutput.setTreeConstructionTime(tTreeConstruction);
        treeConstructionOutput.setScanningTransactionTime(tFileRead);
        treeConstructionOutput.setSufTree(sufTree.copy());
        return treeConstructionOutput;
    }

    private List<SufInputData> getTransaction() throws IOException, DataNotFoundException {
        long fileReadSTime = System.currentTimeMillis();
        String line = bufferedReader.readLine();
        if (line == null) {
            bufferedReader.close();
            return Collections.emptyList();
        }

        List<SufInputData> uNodeList = new ArrayList<SufInputData>();

        String[] transactionItems = line.split(" ");

        for (int i = 0; i < transactionItems.length; i++) {
            String tmpId = transactionItems[i].split("-")[0];
            double tmpPValue = Double.parseDouble(transactionItems[i].split("-")[1]);
            SufInputData data = new SufInputData(tmpId, tmpPValue);
            uNodeList.add(data);
        }
        long fileReadETime = System.currentTimeMillis();
        long timeNeeded = fileReadETime - fileReadSTime;
        this.fileReadTimeNeeded = this.fileReadTimeNeeded + timeNeeded;
        return uNodeList;
    }
}
