package com.mbzshajib.mining.processor.tree.initial;

import com.mbzshajib.mining.exception.CompletedTreeException;
import com.mbzshajib.mining.exception.DataNotValidException;
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
            prepareInitialTree(treeInput.getWindowSize(), treeInput.getFrameSize());
            Utils.log(TAG, "\n\n\n" + ROOT_NODE.traverse());
            while (true) {
                slideFrame(ROOT_NODE);
                addTransactionFrame(treeInput.getFrameSize(), ROOT_NODE, treeInput.getWindowSize() - 1);
                Utils.log(TAG, "\n\n\n" + ROOT_NODE.traverse());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataNotValidException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompletedTreeException e) {
            e.printStackTrace();
        }
        TreeOutput treeOutput = new TreeOutput();
        treeOutput.setRootNode(ROOT_NODE);
        return treeOutput;
    }

    private void prepareInitialTree(int windowSize, int frameSize) throws IOException, DataNotValidException, CompletedTreeException {
        UNode currentNode = ROOT_NODE;
        List<UNode> list;
        for (int i = 0; i < windowSize; i++) {
            currentNode = addTransactionFrame(frameSize, currentNode, i);
        }
    }

    private UNode addTransactionFrame(int frameSize, UNode currentNode, int frameNo) throws IOException, DataNotValidException, CompletedTreeException {
        List<UNode> list;
        for (int j = 0; j < frameSize; j++) {
            list = getTransaction(frameNo);
            if (!list.isEmpty()) {
                throw new CompletedTreeException();
            }
            for (UNode node : list) {
                currentNode = addNode(node, currentNode);
            }
            currentNode = ROOT_NODE;
        }
        return currentNode;
    }

    private void slideFrame(UNode node) {
        assignNewFrameNo(node);
        for (int i = 0; i < node.getChildNodeCount(); i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            if (tmpNode.getFrameNo() == -1) {
                node.getChildNodeList().remove(tmpNode);
            }
        }

    }

    private void assignNewFrameNo(UNode node) {
        for (int i = 0; i < node.getChildNodeCount(); i++) {
            UNode tmpNode = node.getChildNodeList().get(i);
            tmpNode.setFrameNo(tmpNode.getFrameNo() - 1);
            node.getChildNodeList().set(i, tmpNode);
            slideFrame(tmpNode);
        }
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


    private List<UNode> getTransaction(int frameNo) throws IOException, DataNotValidException {
        String line = bufferedReader.readLine();
        if (line == null) {
            return Collections.emptyList();
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
        uNodeList.get(0).setFrameNo(frameNo);
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
