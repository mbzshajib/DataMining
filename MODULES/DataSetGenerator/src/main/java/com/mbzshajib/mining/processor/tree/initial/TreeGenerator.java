package com.mbzshajib.mining.processor.tree.initial;

import com.mbzshajib.mining.exception.DataNotValidException;
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
    private BufferedReader bufferedReader;
    private TreeInput treeInput;

    private static UNode ROOT_NODE;
    private static HeaderTable HEADER_TABLE;

    @Override
    public TreeOutput process(TreeInput treeInput) throws ProcessingError {
        this.treeInput = treeInput;
        try {
            INITIALIZE();
            List<UNode> list;
            while ((list = getTransaction()) != null) {
                addToTree(list);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotValidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addToTree(List<UNode> list) {
        List<UNode> childList = ROOT_NODE.getChildNodeList();
    }

    private void INITIALIZE() throws ProcessingError, FileNotFoundException, DataNotValidException {
        bufferedReader = new BufferedReader(new FileReader(new File(treeInput.getInputFilePath())));
        ROOT_NODE = new UNode("0-0");
        ROOT_NODE.setParentNode(null);
        HEADER_TABLE = new HeaderTable();
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


    private List<UNode> getTransaction() throws IOException, DataNotValidException {
        String line = bufferedReader.readLine();
        if (line == null) {
            return null;
        }
        String[] readedTransactionItems = line.split(" ");
        List<UNode> uNodeList = new ArrayList<UNode>();
        for (String string : readedTransactionItems) {
            UNode uNode = new UNode(string);
            uNodeList.add(uNode);
        }
        assignPrefixValueToTransactionList(uNodeList);
        return uNodeList;
    }

    private void assignPrefixValueToTransactionList(List<UNode> uNodeList) {
        double prefixValue = uNodeList.get(0).getItemProbability();
        uNodeList.get(0).setPrefixValue(prefixValue);
        double maxPrefixValue = prefixValue;
        for (int i = 1; i < uNodeList.size(); i++) {
            UNode uNode = uNodeList.get(i);
            uNode.setPrefixValue(uNode.getItemProbability() * maxPrefixValue);
            if (maxPrefixValue < uNode.getItemProbability()) {
                maxPrefixValue = uNode.getItemProbability();
            }
            uNodeList.set(i, uNode);
        }
    }

}
