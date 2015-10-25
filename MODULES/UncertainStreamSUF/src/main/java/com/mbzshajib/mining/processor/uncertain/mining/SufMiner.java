package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.*;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/21/2015
 * @time: 7:57 PM
 * ****************************************************************
 */

public class SufMiner implements Processor<SufMiningInput, SufMiningOutput> {
    private List<FrequentItem> frequentItemList;
    private double minSupport;
    private SufTree sufTree;

    @Override
    public SufMiningOutput process(SufMiningInput sufMiningInput) throws ProcessingError {
        long startTime = System.currentTimeMillis();
        frequentItemList = new ArrayList<FrequentItem>();
        this.minSupport = sufMiningInput.getMinSupport();
        this.sufTree = sufMiningInput.getSufTree();
        SufNode rootNode = sufTree.getRootNode();
        SufHeader header = sufTree.getHeader();
        header.removeInfrequentItems(minSupport);
        header.sortByProbabilityValueDesc();
        for (SufHItem itemList : header.getItemList()) {
            SufNode copy = rootNode.copy();
            FrequentItem frequentItem = new FrequentItem();
            frequentItem.addFrequentItem(itemList.getItemId());
            frequentItemList.add(frequentItem);

            constructConditionalTreeWithItem(copy, itemList.getItemId());
            List<SufNode> leafNodeList = new ArrayList<SufNode>();
            copy.getLeafNodeList(leafNodeList);
//            updateMiningProbability(leafNodeList);
            for (SufNode leaf : leafNodeList) {
                updateMiningData(leaf, leaf.getMiningProbability());
            }
            SufHeader newHeader = createHeaderForMining(copy);
            newHeader.removeItemById(itemList.getItemId());

            mine(copy, itemList.getItemId(), frequentItem, true);
        }
        SufMiningOutput sufMiningOutput = new SufMiningOutput();
        sufMiningOutput.setFrequentItemList(this.frequentItemList);
        TimeModel timeModel = new TimeModel(startTime, System.currentTimeMillis());
        sufMiningOutput.setMiningTime(timeModel);
        return sufMiningOutput;
    }

    private void mine(SufNode rootNode, String id, FrequentItem frequentItem, boolean isFirstItaration) {
        frequentItem.addFrequentItem(id);
        if (!isFirstItaration) {
            frequentItemList.add(frequentItem);
        }
        frequentItem = new FrequentItem(frequentItem);
        SufHeader header = createHeaderForMining(rootNode);
        updateFrequentItem(header, frequentItem);
        header.removeInfrequentItemsForMining(minSupport);
        for (SufHItem item : header.getItemList()) {
            frequentItem = new FrequentItem(frequentItem);
            frequentItem.addFrequentItem(item.getItemId());
            List<SufNode> leafList = new ArrayList<SufNode>();
            rootNode.getLeafNodeList(leafList);
            for (SufNode leaf : leafList) {
                updateMiningDataForProjectedDb(leaf, leaf.getMiningProbability());
            }
            SufNode copy = rootNode.copy();
            SufHeader headerForMining = createHeaderForMining(copy);
            headerForMining.removeInfrequentItems(minSupport);
            mine(copy, item.getItemId(), frequentItem, true);
        }


    }


    private SufHeader createHeader(SufNode copy) {
        SufHeader sufHeader = new SufHeader();
        List<SufNode> distinctList = copy.getAllDistinctChild(new ArrayList<SufNode>());

        sufHeader.updateFromAllNodes(distinctList);
        return sufHeader;
    }

    private SufHeader createHeaderForMining(SufNode copy) {
        SufHeader sufHeader = new SufHeader();
        List<SufNode> distinctList = copy.getAllDistinctChild(new ArrayList<SufNode>());

        sufHeader.updateFromAllNodesForMining(distinctList);
        return sufHeader;
    }


    private void updateFrequentItem(SufHeader header, FrequentItem frequentItem) {
        for (SufHItem item : header.getItemList()) {
            if (item.getMiningSupport() < minSupport) {

            } else {
                FrequentItem itemToBeInsert = new FrequentItem(frequentItem);
                itemToBeInsert.addFrequentItem(item.getItemId());
                frequentItemList.add(itemToBeInsert);
            }
        }
    }

    private void updateMiningDataForProjectedDb(SufNode leaf, double miningProbability) {
        SufNode pointerNode = leaf;
        while (!(pointerNode.getId().equals("0") || pointerNode.getParentNode().getId().equals(""))) {
            pointerNode.getParentNode().setMiningProbability(0);
            pointerNode = pointerNode.getParentNode();
        }
        SufNode parentNode = leaf.getParentNode();
        if (leaf.getId().equals("0") || parentNode.getId().equals("0")) {
            return;
        } else {
            parentNode.setMiningProbability(parentNode.getMiningProbability() + miningProbability * parentNode.getProbability());
        }
    }

    private void updateMiningData(SufNode sufNode, double value) {

        SufNode parentNode = sufNode.getParentNode();
        if (sufNode.getId().equals("0") || parentNode.getId().equals("0")) {
            return;
        } else {
            parentNode.setMiningProbability(parentNode.getMiningProbability() + value * parentNode.getProbability());
            updateMiningData(parentNode, value);
        }

    }

    private SufNode constructConditionalTreeWithItem(SufNode node, String id) {
        if (id.equals(node.getId())) {
            node.setChildes(new ArrayList<SufNode>());
            node.setMiningProbability(node.getSupport());
            return node;
        } else {
            if (node.getChildes() == null || node.getChildes().isEmpty()) {
                return null;
            } else {
                SufNode returnNode = null;
                int count = node.getChildes().size();
                for (int index = 0; index < count; index++) {
                    SufNode child = node.getChildes().get(index);
                    SufNode foundNode = constructConditionalTreeWithItem(child, id);
                    if (foundNode == null) {
                        node.getChildes().remove(child);
                        index--;
                        count--;
                    } else {
                        returnNode = foundNode;
                    }
                }
                return returnNode;
            }
        }
    }
}
