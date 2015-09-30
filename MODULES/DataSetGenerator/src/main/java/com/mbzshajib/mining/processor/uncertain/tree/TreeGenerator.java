package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.model.UNode;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/20/2015
 * time: 11:03 PM
 * ****************************************************************
 */

public class TreeGenerator implements Processor<TreeConstructionInput, TreeConstructionOutput> {
    private static final String TAG = TreeGenerator.class.getCanonicalName();
    private BufferedReader bufferedReader;
    private TreeConstructionInput treeConstructionInput;
    private long endTime;
    private long startTime;

    @Override
    public TreeConstructionOutput process(TreeConstructionInput treeConstructionInput) throws ProcessingError {
        this.treeConstructionInput = treeConstructionInput;
        UncertainTree uncertainTree = null;
        try {
            initialize();
            uncertainTree = new UncertainTree(treeConstructionInput.getFrameSize(), treeConstructionInput.getWindowSize());
            for (int frameNo = 0; frameNo < treeConstructionInput.getWindowSize(); frameNo++) {
                for (int i = 0; i < treeConstructionInput.getFrameSize(); i++) {
                    List<UNode> nodes = getTransaction(frameNo);
                    uncertainTree.addTransactionToTree(nodes);
                }
//                Utils.log(TAG, "FRAME NO " + frameNo + " has been added ");
//                Utils.log(TAG, uncertainTree.getTraversedString());
            }
            uncertainTree.slideWindowAndUpdateTree();
            List<UNode> nodes = null;
            int frameCounter = 0;
            while (!(nodes = getTransaction(treeConstructionInput.getWindowSize() - 1)).isEmpty()) {
                if (!(frameCounter < treeConstructionInput.getWindowSize())) {
//                    Utils.log(TAG, "FRAME NO " + (treeConstructionInput.getWindowSize() - 1) + " has been added ");
//                    Utils.log(TAG, uncertainTree.getTraversedString());
                    frameCounter = 0;
                    uncertainTree.slideWindowAndUpdateTree();
                }
                uncertainTree.addTransactionToTree(nodes);
                frameCounter++;

            }
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotValidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeConstructionOutput treeConstructionOutput = new TreeConstructionOutput();
        treeConstructionOutput.setUncertainTree(uncertainTree);
        treeConstructionOutput.setStartTime(startTime);
        treeConstructionOutput.setEndTime(endTime);
        return treeConstructionOutput;
    }

    private void finish() {
        endTime = System.currentTimeMillis();
    }


    private void initialize() throws ProcessingError, FileNotFoundException, DataNotValidException {
        startTime = System.currentTimeMillis();
        bufferedReader = new BufferedReader(new FileReader(new File(treeConstructionInput.getInputFilePath())));
    }


    private List<UNode> getTransaction(int frameNo) throws IOException, DataNotValidException {
        String line = bufferedReader.readLine();
        if (line == null) {
            bufferedReader.close();
            return Collections.emptyList();
        }

        List<UNode> uNodeList = new ArrayList<UNode>();

        String[] transactionItems = line.split(" ");
        UNode firstNode = new UNode(transactionItems[0]);
        double maxProbability = firstNode.getItemProbability();
        firstNode.setPrefixValue(maxProbability);
        firstNode.setFrameNo(frameNo);
        uNodeList.add(firstNode);
        for (int i = 1; i < transactionItems.length; i++) {
            UNode uNode = new UNode(transactionItems[i]);
            uNode.setFrameNo(frameNo);
            double prefixValueToBeAssigned = maxProbability * uNode.getItemProbability();
            uNode.setPrefixValue(prefixValueToBeAssigned);
            uNodeList.add(uNode);
            if (maxProbability < uNode.getItemProbability()) {
                maxProbability = uNode.getItemProbability();
            }
        }
        return uNodeList;
    }
}
