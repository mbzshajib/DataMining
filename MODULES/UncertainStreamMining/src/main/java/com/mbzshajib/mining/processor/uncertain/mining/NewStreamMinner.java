package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.UHItem;
import com.mbzshajib.mining.processor.uncertain.model.UHeader;
import com.mbzshajib.mining.processor.uncertain.model.UNode;
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

    @Override
    public UncertainStreamMineOutput process(UncertainStreamMineInput uncertainStreamMineInput) throws ProcessingError {
        fRootNode = new FNode();
        double minSup = uncertainStreamMineInput.getMinSupport();
        UNode rootNode = uncertainStreamMineInput.getUncertainTree().getRootNode();
        System.out.println("Minimum Support = " + minSup);
        System.out.println("Before Mining Start.");
        System.out.println(rootNode.traverseMin());
        List<UNode> distinctNodes = rootNode.getDistinctNodes();
        UHeader header = new UHeader();
        for (UNode distinct : distinctNodes) {
            header.addNodeToHeader(distinct);
        }
        header.sortBySupportDsc();
        List<UHItem> itemList = header.getItemList();
        for (int i = 0; i < itemList.size(); i++) {
            UHItem item = itemList.get(i);
            if (item.getTotalSupport() < minSup) {
                header.removeItemFromListWithNodes(item);
            } else {
                break;
            }
        }
        System.out.println("After removing One Item Infrequent");
        System.out.println(rootNode.traverseMin());
        for (UHItem item : header.getItemList()) {
            fRootNode.addChildesChain(new String[]{item.getItemId()});
        }
//        System.out.println(header.traverse());
        header.sortByPrefixDsc();
        for (UHItem item : header.getItemList()) {
            UNode conditionalRoot = rootNode.copy();
            String id = item.getItemId();
            constructConditionalTreeNotRemovingChild(conditionalRoot, id);
            System.out.println("Generated Conditional Tree Step 1 for ID : " + id);
            System.out.println(conditionalRoot.traverseMin());
            List<UNode> leafNodeList = new ArrayList<UNode>();
            conditionalRoot.getLeafNodeList(leafNodeList);
            updateMiningProbability(leafNodeList);
            for (UNode leaf : leafNodeList) {
                if (leaf.getParentNode() != null) {
                    UNode parent = leaf.getParentNode();
                    parent.getChildNodeList().remove(leaf);
                    leaf.setParentNode(null);
                }
            }
            System.out.println("Generated Conditional Tree Step 2 for ID : " + id);
            System.out.println(conditionalRoot.traverseMin());
            mine(conditionalRoot, minSup, new FrequentItem(id));
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

        return null;
    }

    public void mine(UNode rootNode, double minSup, FrequentItem frequentItem) {
        UHeader header = new UHeader();
        List<UNode> distinctNodes = rootNode.getDistinctNodes();
        for (UNode distinct : distinctNodes) {
            header.addNodeToHeader(distinct);
        }
        header.sortByPrefixDsc();
        List<UHItem> itemList = header.getItemList();
        for (int i = 0; i < itemList.size(); i++) {
            UHItem item = itemList.get(i);
            if (item.getTotalMiningVal() < minSup) {
                header.removeItemFromListWithNodes(item);
            } else {
                break;
            }

        }

        //TODO: Create Header With Mining Probability Only
        //TODO: Remove Item Less Than min_sup by mining Value
        //TODO: ADD PATTERNS TO ROOT_F_PATTERNS
        //TODO: LOOP(EACH ITEM IN HEADER)
        //  TODO: COPY ROOT
        //  TODO: REMOVE ITEM FROM COPY TREE
        //  TODO: ADD ITEM TO EXISTING frequentItem
        //  TODO: MINE(COPY, minsup, created frequentItem).
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

    private void updateMiningProbability(List<UNode> leafNodeList) {
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
