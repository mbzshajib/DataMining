package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.*;
import com.mbzshajib.utility.collection.PowerSetGenerator;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;
import com.mbzshajib.utility.model.fpatterns.FNode;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/29/2015
 * @time: 7:56 PM
 * ****************************************************************
 */

public class NewStreamMinner implements Processor<UncertainStreamMineInput, UncertainStreamMineOutput> {
    private FNode fRootNode;
    private int mine = 0;

    @Override
    public UncertainStreamMineOutput process(UncertainStreamMineInput uncertainStreamMineInput) throws ProcessingError {
        long startTime = System.currentTimeMillis();
        fRootNode = new FNode();
        double minSup = uncertainStreamMineInput.getMinSupport();
        UNode rootNode = uncertainStreamMineInput.getUncertainTree().getRootNode();
//        System.out.println("Minimum Support = " + minSup);
//        System.out.println("Before Mining Start.");
//        System.out.println(rootNode.traverseMin());
        List<UNode> distinctNodes = rootNode.getDistinctNodes();
        UHeader header = new UHeader();
        for (UNode distinct : distinctNodes) {
            header.addNodeToHeader(distinct);
        }
        header.sortBySupportDsc();
        List<UHItem> itemList = header.getItemList();
        int counter = itemList.size();
        for (int i = 0; i < counter; i++) {

            UHItem item = itemList.get(i);
            double sup = item.getTotalSupport();
            if (item.getTotalSupport() < minSup) {
                header.removeItemFromListWithNodes(item);
                counter = counter - 1;
                i = i - 1;
            } else {
                break;
            }
        }
//        System.out.println("After removing One Item Infrequent");
//        System.out.println(rootNode.traverseMin());
        for (UHItem item : header.getItemList()) {
            fRootNode.addChildesChain(new String[]{item.getItemId()});
        }
        header.sortByPrefixDsc();
        for (UHItem item : header.getItemList()) {
            UNode conditionalRoot = rootNode.copy();
            String id = item.getItemId();
            constructConditionalTreeNotRemovingChild(conditionalRoot, id);
//            System.out.println("Generated Conditional Tree Step 1 for ID : " + id);
//            System.out.println(conditionalRoot.traverseMin());
            List<UNode> leafNodeList = new ArrayList<UNode>();
            conditionalRoot.getLeafNodeList(leafNodeList);
            updateMiningProbabilityForCondTree(leafNodeList);
            for (UNode leaf : leafNodeList) {
                if (leaf.getParentNode() != null) {
                    UNode parent = leaf.getParentNode();
                    parent.getChildNodeList().remove(leaf);
                    leaf.setParentNode(null);
                }
            }
            if (leafNodeList.size() == 0) {

            }
            if (leafNodeList.size() == 1) {
                updateFrequentItemForOneBranch(conditionalRoot, new FrequentItem(id));
            } else {
                mine(conditionalRoot, minSup, new FrequentItem(id));
            }
//            System.out.println("Generated Conditional Tree Step 2 for ID : " + id);
//            System.out.println(conditionalRoot.traverseMin());
        }
        //TODO: Create Header Table
        //TODO: Remove 1 ItemSet Infrequent
        //TODO: Add One Item Frequent Patterns
        //TODO: For Each Item in header:
        //TODO: LOOP
        //  TODO: COPY ROOT
        //  TODO: Create Conditional Tree
        //  TODO: Update probability
        //  TODO: Remove Leaf List
        //  TODO: MINE(root,minSup,CandidateItemSet)
//        System.out.println();
//        System.out.println();
//        System.out.println(fRootNode.traverse());
        UncertainStreamMineOutput output = new UncertainStreamMineOutput();
        output.setFrequentItemList(null);
        output.setFrequentNodesRoot(this.fRootNode);
        TimeModel timeModel = new TimeModel(startTime, System.currentTimeMillis());
        output.setMiningTime(timeModel);
        return output;
    }

    private void updateFrequentItemForOneBranch(UNode root, FrequentItem frequentItem) {
        List<UNode> nodeList = new ArrayList<UNode>();
        UNode child;
        UNode parent = root;
        while (parent.getChildNodeList().size() == 1) {
            child = parent.getChildNodeList().get(0);
            nodeList.add(child);
            parent = child;
        }
        PowerSetGenerator<UNode> generator = new PowerSetGenerator<UNode>();
        List<List<UNode>> powerSet = generator.generatePowerSet(nodeList);
        if (powerSet.size() > 1) {
            for (List<UNode> set : powerSet) {
                for (UNode item : set) {
                    frequentItem.addFrequentItem(item.getId());
                }
            }
            String[] frequentItemSet = frequentItem.getFrequentItemSet();
            fRootNode.addChildesChain(frequentItemSet);
        }

    }

    public void mine(UNode rootNode, double minSup, FrequentItem frequentItem) {
//        System.out.println(mine++);
        UHeader header = new UHeader();
        List<UNode> distinctNodes = rootNode.getDistinctNodes();
        for (UNode distinct : distinctNodes) {
            header.addNodeToHeader(distinct);
        }
        header.sortByMiningValueDsc();
        List<UHItem> itemList = header.getItemList();
        int count = itemList.size();
        for (int i = 0; i < count; i++) {
            UHItem item = itemList.get(i);
            if (item.getTotalMiningVal() < minSup) {
                header.removeItemFromListWithNodes(item);
                i--;
                count--;
            } else {
                break;
            }

        }
        updateFrequentItem(header, frequentItem);
        for (UHItem item : header.getItemList()) {
            UNode copy = rootNode.copy();
            UHeader copyHeader = new UHeader();
            List<UNode> copyDistinctNodes = copy.getDistinctNodes();
            for (UNode distinct : copyDistinctNodes) {
                copyHeader.addNodeToHeader(distinct);
            }
            copyHeader.removeItemByIdWithNode(item.getItemId());

            List<UNode> leafList = new ArrayList<UNode>();
            copy.getLeafNodeList(leafList);
            updateMiningProbability(leafList);

            FrequentItem copyFrequent = new FrequentItem(frequentItem);
            copyFrequent.addFrequentItem(item.getItemId());
            if (leafList.size() == 1) {
                updateFrequentItemForOneBranch(copy, frequentItem);
            } else {
                mine(copy, minSup, copyFrequent);
            }

        }
        //TODO: Create Header With Mining Probability Only
        //TODO: Remove Item Less Than min_sup by mining Value
        //TODO: ADD PATTERNS TO ROOT_F_PATTERNS
        //TODO: LOOP(EACH ITEM IN HEADER)
        //  TODO: COPY ROOT
        //  TODO: REMOVE ITEM FROM COPY TREE HEADER ACTUALLY
        //  TODO: ADD ITEM TO EXISTING frequentItem
        //  TODO: MINE(COPY, minsup, created frequentItem).
    }

    private void updateMiningProbability(List<UNode> leafList) {
        for (UNode leaf : leafList) {
            if (!leaf.getId().equals("0")) {
                UNode parent = leaf.getParentNode();
                while (!parent.getId().equals("0")) {
                    parent.setMiningProbability(0);
                    parent = parent.getParentNode();
                }

            }
            updateParentMiningData(leaf);

        }
    }

    private void updateFrequentItem(UHeader header, FrequentItem fItem) {
        for (UHItem hItem : header.getItemList()) {
            FrequentItem frequentItem = new FrequentItem(fItem);
            frequentItem.addFrequentItem(hItem.getItemId());
            String[] frequentItemSet = frequentItem.getFrequentItemSet();
            fRootNode.addChildesChain(frequentItemSet);
        }
    }

    private UNode constructConditionalTreeNotRemovingChild(UNode node, String id) {
        if (node.getId().equalsIgnoreCase(id)) {
            node.setChildNodeList(new ArrayList<UNode>());
//            node.getParentNode().setMiningProbability(node.getNodePrefixValue());
            return node;
        } else {
            if (node.getChildNodeList() == null || node.getChildNodeList().isEmpty()) {
                return null;
            } else {
                UNode returnNode = null;
                int count = node.getChildNodeList().size();
                for (int index = 0; index < count; index++) {
                    UNode child = node.getChildNodeList().get(index);
                    UNode foundNode = constructConditionalTreeNotRemovingChild(child, id);
                    if (foundNode == null) {
                        node.removeChildNode(child);
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

    private void updateMiningProbabilityForCondTree(List<UNode> leafNodeList) {
        for (UNode leafNode : leafNodeList) {
            leafNode.setMiningProbability(leafNode.getTotalPrefix());
            updateParentMiningData(leafNode);
        }

    }

    private void updateParentMiningData(UNode leafNode) {
        UNode parentNode = leafNode.getParentNode();
        if (leafNode.getId().equals("0") || parentNode.getId().equals("0")) {
            return;
        } else {
            parentNode.setMiningProbability(parentNode.getMiningProbability() + leafNode.getMiningProbability());
            updateParentMiningData(parentNode);
        }
    }

}
