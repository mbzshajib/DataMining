package com.mbzshajib.mining.processor.uncertain;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
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
        frequentItemList = new ArrayList<FrequentItem>();
        this.minSupport = sufMiningInput.getMinSupport();
        this.sufTree = sufMiningInput.getSufTree();
        SufNode rootNode = sufTree.getRootNode();
        SufHeader header = sufTree.getHeader();
        header.removeInfrequentItems(minSupport);
        header.sortByProbabilityValueDesc();
        for (SufHItem itemList : header.getItemList()) {
            SufNode copy = rootNode.copy();
            SufHeader table = createHeader(copy);
            table.sortByProbabilityValueDesc();
            FrequentItem frequentItem = new FrequentItem();
            frequentItem.addFrequentItem(itemList.getItemId());
            frequentItemList.add(frequentItem);
            mine(copy, itemList.getItemId(), frequentItem, true);
        }
        System.out.println(frequentItemList);
        return null;
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

    private void mine(SufNode rootNode, String id, FrequentItem frequentItem, boolean isFirstItaration) {
        frequentItem.addFrequentItem(id);
        if (!isFirstItaration) {
            frequentItemList.add(frequentItem);
        }
        frequentItem = new FrequentItem(frequentItem);
        constructConditionalTreeWithItem(rootNode, id);
        List<SufNode> leafNodeList = new ArrayList<SufNode>();
        rootNode.getLeafNodeList(leafNodeList);
        updateMiningProbability(leafNodeList);
        SufHeader header = createHeaderForMining(rootNode);
        header.removeItemById(id);
//        createHeader(rootNode);
        header = createHeaderForMining(rootNode);
        header.removeInfrequentItemsForMining(minSupport);
        updateFrequentItem(rootNode, frequentItem);
        for (SufHItem item : header.getItemList()) {
            frequentItem = new FrequentItem(frequentItem);
            mine(rootNode, item.getItemId(), frequentItem, false);
        }


    }

    private void updateFrequentItem(SufNode rootNode, FrequentItem item) {
        for (int i = 0; i < rootNode.getChildes().size(); i++) {
            SufNode child = rootNode.getChildes().get(i);
            FrequentItem tmpItem = new FrequentItem(item);
            tmpItem.addFrequentItem(child.getId());
            if (child.getMiningProbability() > minSupport) {
                frequentItemList.add(tmpItem);
            }
            updateFrequentItem(child, item);
        }
    }

    private void updateMiningProbability(List<SufNode> leafNodeList) {
        for (SufNode sufNode : leafNodeList) {
            sufNode.setMiningProbability(sufNode.getSupport());
            while (sufNode != null && sufNode.getParentNode() != null && !sufNode.getParentNode().getId().equals("0")) {
                sufNode.getParentNode().setMiningProbability(0);
                sufNode = sufNode.getParentNode();
            }
        }
        for (SufNode sufNode : leafNodeList) {
            sufNode.setMiningProbability(sufNode.getSupport());
            updateMiningData(sufNode, sufNode.getMiningProbability());
        }
    }

    private void updateMiningData(SufNode sufNode, double value) {

        SufNode parentNode = sufNode.getParentNode();
        if (sufNode.getId().equals("0") || parentNode.getId().equals("0")) {
            return;
        } else {
            parentNode.setMiningProbability(parentNode.getMiningProbability() + value*parentNode.getProbability());
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
