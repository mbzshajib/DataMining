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
    private BufferedReader bufferedReader;
    private TreeInput treeInput;

    private UNode ROOT_NODE;
    private static HeaderTable HEADER_TABLE;

    @Override
    public TreeOutput process(TreeInput treeInput) throws ProcessingError {
        this.treeInput = treeInput;
        try {
            INITIALIZE();
            UNode currentNode = ROOT_NODE;
            List<UNode> list;
            int windowSize = treeInput.getWindowSize();
            int frameSize = treeInput.getFrameSize();
            for (int i = 0; i < windowSize; i++) {
                for (int j = 0; j < frameSize; j++) {
                    list = getTransaction(i);
                    for (UNode node : list) {
                        currentNode = addNode(node, currentNode);
                    }
                    currentNode=ROOT_NODE;

                }
            }
            Utils.log(TAG,ROOT_NODE.travase());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotValidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UNode addNode(UNode node, UNode currentNode) {
        int foundIndex = -1;
        for (int i = 0; i < currentNode.getChildNodeList().size(); i++) {
            if (currentNode.getChildNodeList().get(i).isSameID(node)) {
                foundIndex = i;
            }
        }
        if (foundIndex == -1) {
            currentNode.addChild(node);
            return node;
        } else {
            UNode tmpNode = currentNode.getChildNodeList().get(foundIndex);
            tmpNode.setPrefixValue(tmpNode.getPrefixValue() + node.getPrefixValue());
            currentNode.getChildNodeList().set(foundIndex, tmpNode);
            return currentNode.getChildNodeList().get(foundIndex);
        }

    }

    private void INITIALIZE() throws ProcessingError, FileNotFoundException, DataNotValidException {
        bufferedReader = new BufferedReader(new FileReader(new File(treeInput.getInputFilePath())));
        ROOT_NODE = new UNode("0-0");
        ROOT_NODE.setParentNode(null);
        HEADER_TABLE = new HeaderTable();
    }


    private void updateSameNode(UNode child, UNode tmpNode) {
        child.setFrameNo(child.getChildNodeCount() + 1);
    }


    private List<UNode> getTransaction(int frameNo) throws IOException, DataNotValidException {
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
        assignPrefixValueToTransactionList(uNodeList, frameNo);
        return uNodeList;
    }

    private void assignPrefixValueToTransactionList(List<UNode> uNodeList, int frameNo) {
        double prefixValue = uNodeList.get(0).getItemProbability();
        uNodeList.get(0).setPrefixValue(prefixValue);
        double maxPrefixValue = prefixValue;
        for (int i = 1; i < uNodeList.size(); i++) {
            UNode uNode = uNodeList.get(i);
            uNode.setPrefixValue(uNode.getItemProbability() * maxPrefixValue);
            uNode.setFrameNo(frameNo);
            if (maxPrefixValue < uNode.getItemProbability()) {
                maxPrefixValue = uNode.getItemProbability();
            }
            uNodeList.set(i, uNode);
        }
    }

}
