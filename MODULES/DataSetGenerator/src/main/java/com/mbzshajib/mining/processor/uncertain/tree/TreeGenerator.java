package com.mbzshajib.mining.processor.uncertain.tree;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.model.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.model.UNode;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.util.Utils;
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
    @Override
    public TreeConstructionOutput process(TreeConstructionInput treeConstructionInput) throws ProcessingError {
        this.treeConstructionInput = treeConstructionInput;
        UncertainTree uncertainTree = null;
        try {
//            1-0.52 5-0.39 12-0.51 21-0.31 23-0.46 25-0.48 36-0.54 39-0.54 42-0.33
//            1-0.44 2-0.5 12-0.57 21-0.4 23-0.33 25-0.56 36-0.44 39-0.6 42-0.48
//            2-0.45 5-0.61 12-0.53 21-0.37 23-0.41 25-0.47 36-0.42 39-0.57 42-0.39
//            2-0.64 5-0.52 12-0.61 21-0.48 23-0.31 25-0.65 36-0.41 39-0.63 42-0.57
//            1-0.55 5-0.65 12-0.4 21-0.44 23-0.51 25-0.38 36-0.39 39-0.5 42-0.38
//            1-0.53 5-0.59 12-0.55 21-0.65 23-0.58 25-0.64 36-0.65 39-0.57 42-0.41
//            1-0.47 5-0.51 12-0.42 21-0.55 23-0.59 26-0.32 36-0.47 39-0.58 42-0.48
//            1-0.43 5-0.56 12-0.38 21-0.37 22-0.42 26-0.65 36-0.52 39-0.48 42-0.34
//            1-0.51 5-0.46 12-0.65 21-0.42 23-0.44 26-0.62 36-0.49 39-0.47 42-0.41
//            1-0.61 5-0.38 12-0.55 21-0.35 23-0.33 26-0.42 36-0.56 39-0.49 42-0.43
//            1-0.6 5-0.55 12-0.33 21-0.6 23-0.54 26-0.52 36-0.41 39-0.42 42-0.59
//            1-0.65 5-0.37 7-0.53 21-0.59 23-0.6 26-0.35 36-0.44 39-0.33 42-0.48
//            1-0.58 5-0.37 7-0.43 22-0.33 23-0.33 25-0.4 36-0.35 39-0.54 42-0.36
//            1-0.55 5-0.37 7-0.59 22-0.48 23-0.56 25-0.38 36-0.51 39-0.56 42-0.48
//            1-0.43 5-0.33 7-0.37 22-0.54 23-0.48 25-0.33 36-0.61 39-0.62 42-0.63
//            1-0.42 5-0.48 12-0.58 22-0.35 23-0.46 25-0.37 36-0.41 39-0.34 42-0.55
//            1-0.37 5-0.45 12-0.37 22-0.46 23-0.51 25-0.41 36-0.65 39-0.38 42-0.48
//            1-0.64 5-0.41 12-0.41 22-0.42 23-0.47 25-0.38 36-0.49 39-0.62 42-0.36
            initialize();
            uncertainTree = new UncertainTree(treeConstructionInput.getFrameSize(), treeConstructionInput.getWindowSize());
            for (int frameNo = 0; frameNo < treeConstructionInput.getWindowSize(); frameNo++) {
                for (int i = 0; i < treeConstructionInput.getFrameSize(); i++) {
                    List<UNode> nodes = getTransaction(frameNo);
                    uncertainTree.addTransactionToTree(nodes);
                }
                Utils.log(TAG, "FRAME NO " + frameNo + " has been added ");
                Utils.log(TAG, uncertainTree.getTraversedString());
            }
            uncertainTree.slideWindowAndUpdateTree();
            List<UNode> nodes = null;
            int frameCounter = 0;
            while (!(nodes = getTransaction(treeConstructionInput.getWindowSize() - 1)).isEmpty()) {
                uncertainTree.addTransactionToTree(nodes);
                frameCounter++;
                if (!(frameCounter < treeConstructionInput.getWindowSize())) {
                    Utils.log(TAG, "FRAME NO " + (treeConstructionInput.getWindowSize() - 1) + " has been added ");
                    Utils.log(TAG, uncertainTree.getTraversedString());
                    frameCounter = 0;
                    uncertainTree.slideWindowAndUpdateTree();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotValidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeConstructionOutput treeConstructionOutput = new TreeConstructionOutput();
        treeConstructionOutput.setRootNode(uncertainTree.getRootNode());
        return treeConstructionOutput;
    }


    private void initialize() throws ProcessingError, FileNotFoundException, DataNotValidException {
        bufferedReader = new BufferedReader(new FileReader(new File(treeConstructionInput.getInputFilePath())));
    }


    private List<UNode> getTransaction(int frameNo) throws IOException, DataNotValidException {
        String line = bufferedReader.readLine();
        if (line == null) {
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
