package com.mbzshajib.mining.processor.tree.initial;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.util.Utils;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.io.*;
import java.util.ArrayList;
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

public class TreeGenerator implements Processor<TreeInput, TreeOutput> {
    private static final String TAG = TreeGenerator.class.getCanonicalName();
    private int TRANSACTION_NO = 1;
    private BufferedReader bufferedReader;
    private TreeInput treeInput;
    private UNode ROOT_NODE = null;

    @Override
    public TreeOutput process(TreeInput treeInput) throws ProcessingError {

        initialize(treeInput);
        String[] cTransactionData;
        try {
            while ((cTransactionData = getATransactionData()) != null) {
                Utils.log(TAG, "Transaction No " + TRANSACTION_NO++);
                List<UNode> currentTransactionList = new ArrayList<UNode>();
                for (String s : cTransactionData) {
                    UNode tmpUNode;
                    try {
                        tmpUNode = new UNode(s);
                    } catch (DataNotValidException e) {
                        throw new ProcessingError(e);

                    }
                    currentTransactionList.add(tmpUNode);
                }
                updateTree(currentTransactionList);

            }
        } catch (IOException e) {
            throw new ProcessingError(e);
        }

        return null;
    }

    private void updateTree(List<UNode> currentTransactionList) {
        UNode currentPointer = ROOT_NODE;
        for (UNode tmpNode : currentTransactionList) {
            List<UNode> childList = currentPointer.getChildNodeList();
            boolean sameNode = false;
            for (UNode child : childList) {
                if (child.isSameID(tmpNode)) {
                    sameNode = true;
                    updateSameNode(child, tmpNode);
                }
            }
            if (sameNode == false) {
                currentPointer.addChild(tmpNode);
                tmpNode.setParentNode(currentPointer);
            }
            currentPointer = tmpNode;
        }
        System.out.println(ROOT_NODE.toString());
    }

    private void updateSameNode(UNode child, UNode tmpNode) {
        child.setNodeCount(child.getChildNodeCount() + 1);
    }


    private void initialize(TreeInput treeInput) throws ProcessingError {
        this.treeInput = treeInput;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(treeInput.getInputFilePath())));
        } catch (FileNotFoundException e) {
            throw new ProcessingError(e);
        }
        try {
            ROOT_NODE = new UNode("0-0");
            ROOT_NODE.setParentNode(null);
        } catch (DataNotValidException e) {
            throw new ProcessingError(e);
        }
    }

    private String[] getATransactionData() throws IOException {
        String line = bufferedReader.readLine();
        if (line == null) {
            return null;
        }
        return line.split(" ");
    }

}
