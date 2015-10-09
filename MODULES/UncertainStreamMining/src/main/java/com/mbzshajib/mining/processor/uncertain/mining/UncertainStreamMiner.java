package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.processor.uncertain.model.*;
import com.mbzshajib.mining.util.Constant;
import com.mbzshajib.mining.util.Utils;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/28/2015
 * @time: 7:46 PM
 * ****************************************************************
 */

public class UncertainStreamMiner implements Processor<UncertainStreamMineInput, UncertainStreamMineOutput> {
    public static final String TAG = UncertainStreamMiner.class.getCanonicalName();
    private List<FrequentItem> frequentItemList;

    @Override
    public UncertainStreamMineOutput process(UncertainStreamMineInput uncertainStreamMineInput) throws ProcessingError {
        frequentItemList = new ArrayList<FrequentItem>();
        UncertainTree uncertainTree = uncertainStreamMineInput.getUncertainTree();
        System.out.println(uncertainTree.getTraversedString());
        double minSupport = uncertainStreamMineInput.getMinSupport();
        try {
            startMining(uncertainTree, minSupport);
        } catch (DataNotValidException e) {
            throw new ProcessingError(e);
        }
        printOutput();
        return null;
//        printBeforeMining(uncertainTree);
//        UNode rootNode = uncertainTree.getRootNode();
//        HeaderTable headerTable = uncertainTree.getHeaderTable();
//        List<HTableItemInfo> HTableItemInfoList = headerTable.getHeaderItemInfo();
//        removeDataBelowSupport(minSupport, HTableItemInfoList);
//        sortByPrefix(HTableItemInfoList);
    }

    private void printOutput() {
        StringBuilder builder = new StringBuilder();
        int count = 1;
        builder.append("Total Frequent Items ")
                .append(Constant.TABBED_HASH)
                .append(frequentItemList.size())
                .append(Constant.NEW_LINE);
        for (FrequentItem item : frequentItemList) {
            builder.append(count)
                    .append(item.traverse())
                    .append(Constant.NEW_LINE);
        }
        System.out.println(builder.toString());
    }

    private void startMining(UncertainTree uncertainTree, double minSupport) throws DataNotValidException {
        UNode rootNode = uncertainTree.getRootNode();
        List<UNode> leafNodeList = new ArrayList<UNode>();
        rootNode.getLeafNodeList(leafNodeList);
        HeaderTable headerTable = uncertainTree.getHeaderTable();
        int windowSize = headerTable.getWindowSize();
        List<HTableItemInfo> inFrequentItemsInfo = getInfrequentItemInfoFromHeader(headerTable, minSupport);
        //TODO:Remove data below support(Probability & Prefix Value).
        removeInfrequentData(rootNode, headerTable, inFrequentItemsInfo, windowSize);
        List<HTableItemInfo> listToBeMined = headerTable.getFrequentItemInfoByPrefix(minSupport);
//        sortByPrefix(listToBeMined);
        //TODO:LOOP
        for (int loopCounter = listToBeMined.size() - 1; loopCounter >= 0; loopCounter--) {
            UNode tmpNode = rootNode.copy();
            FrequentItem frequentItem = new FrequentItem();
            frequentItem.addFrequentItem(listToBeMined.get(loopCounter).getItemId());
            frequentItemList.add(frequentItem);
            mine(tmpNode, frequentItem, windowSize, listToBeMined.get(loopCounter).getItemId(), minSupport, false);
        }


    }

    private void mine(UNode root, FrequentItem item, int windowSize, String id, double minSupport, boolean isFirstIteration) throws DataNotValidException {
        item.addFrequentItem(id);
        if (isFirstIteration) {
            frequentItemList.add(item);
        }
        item = new FrequentItem(item);

        constructConditionalTreeNotRemovingChild(root, id);
        removeInfrequentItemNodes(root, id);
        List<UNode> leafNodeList = new ArrayList<UNode>();
        root.getLeafNodeList(leafNodeList);
        updateMiningProbability(leafNodeList);
        HeaderTable headerTable = generateHeaderTableForCondTree(root, windowSize);
        List<HTableItemInfo> inFrequentItemsInfo = getInfrequentItemInfoForMining(headerTable, minSupport);
        headerTable = removeInfrequentData(root, headerTable, inFrequentItemsInfo, windowSize);
        updateFrequentItem(root, item);
        List<HTableItemInfo> headerItemInfo = headerTable.getHeaderItemInfo();
        for (int i = headerItemInfo.size() - 1; i > 0; i--) {
            item = new FrequentItem(item);
            mine(root, item, windowSize, headerItemInfo.get(i).getItemId(), minSupport, false);
        }
    }

    private List<HTableItemInfo> getInfrequentItemInfoForMining(HeaderTable headerTable, double minSupport) {
        List<HTableItemInfo> inFrequentItemInfoByMiningValue = headerTable.getInFrequentItemInfoByMiningValue(minSupport);
        return inFrequentItemInfoByMiningValue;
    }

    private List<HTableItemInfo> getInfrequentItemInfoFromHeader(HeaderTable headerTable, double minSupport) {
        List<HTableItemInfo> inFrequentItemInfoByPrefix = headerTable.getInFrequentItemInfoByPrefix(minSupport);
        List<HTableItemInfo> inFrequentItemInfoBySupport = headerTable.getInFrequentItemInfoBySupport(minSupport);
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        result.addAll(inFrequentItemInfoByPrefix);
        result.addAll(inFrequentItemInfoBySupport);
        return result;
    }


    private void updateMiningProbability(List<UNode> leafNodeList) {
        for (UNode leafNode : leafNodeList) {
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

    private HeaderTable removeInfrequentData(UNode node, HeaderTable headerTable, List<HTableItemInfo> inFrequentItemInfoByPrefix, int windowSize) throws DataNotValidException {
        for (HTableItemInfo hTableItemInfo : inFrequentItemInfoByPrefix) {
            removeInfrequentItemNodes(node, hTableItemInfo.getItemId());
            headerTable = generateHeaderTableForCondTree(node, windowSize);
        }
        return headerTable;
    }

    private void updateFrequentItem(UNode rootNode, FrequentItem item) {

        for (int i = 0; i < rootNode.getChildNodeList().size(); i++) {
            UNode child = rootNode.getChildNodeList().get(i);
            FrequentItem tmpItem = new FrequentItem(item);
            tmpItem.addFrequentItem(child.getId());
            frequentItemList.add(tmpItem);
            updateFrequentItem(child, item);
        }
    }

    private void removeInfrequentItemNodes(UNode rootNode, String itemId) {
        rootNode.removeNodeIfChildOfAnyDepthById(itemId);
    }

    private HeaderTable generateHeaderTableForCondTree(UNode rootNode, int windowSize) throws DataNotValidException {
        List<UNode> distinctList = rootNode.getDistinctNodes();

        HeaderTable headerTable = new HeaderTable(windowSize);
        headerTable.updateHeaderTableFromDistinctList(distinctList);
        return headerTable;
    }

    @Deprecated
    private UNode constructConditionalTreeRemovingChild(UNode node, String id) {
        if (node.getId().equalsIgnoreCase(id)) {
            UNode parentNode = node.getParentNode();
            parentNode.removeChildNode(node);
            return parentNode;
        } else {
            if (node.getChildNodeList() == null || node.getChildNodeList().isEmpty()) {
                return null;
            } else {
                UNode returnNode = null;
                int count = node.getChildNodeList().size();
                for (int index = 0; index < count; index++) {
                    UNode child = node.getChildNodeList().get(index);
                    UNode foundNode = constructConditionalTreeRemovingChild(child, id);
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

    private void sortByID(List<HTableItemInfo> sortedFilteredById) {
        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
            @Override
            public int compare(HTableItemInfo one, HTableItemInfo two) {
                int idOne = Integer.parseInt(one.getItemId());
                int idTwo = Integer.parseInt(two.getItemId());
                int diff = idTwo - idOne;
                return diff;
            }
        });

    }

    private void sortByPrefix(List<HTableItemInfo> sortedFilteredById) {
        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
            @Override
            public int compare(HTableItemInfo one, HTableItemInfo two) {
                double diff = two.getItemPrefixValue() - one.getItemPrefixValue();
                if (diff == 0) {
                    return 0;
                } else if (diff > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }

    private void sortByProbability(List<HTableItemInfo> sortedFilteredById) {
        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
            @Override
            public int compare(HTableItemInfo one, HTableItemInfo two) {
                double diff = two.getItemProbabilityValue() - one.getItemProbabilityValue();
                if (diff == 0) {
                    return 0;
                } else if (diff > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }

    @Deprecated
    private void removeDataBelowSupport(double minSupport, List<HTableItemInfo> HTableItemInfoList) {
        removePrefixDataBelowSupport(HTableItemInfoList, minSupport);
        removeProbabilityDataBelowSupport(HTableItemInfoList, minSupport);
    }

    @Deprecated
    private void removePrefixDataBelowSupport(List<HTableItemInfo> HTableItemInfoList, double minSupport) {
        for (int index = 0; index < HTableItemInfoList.size(); index++) {
            HTableItemInfo HTableItemInfo = HTableItemInfoList.get(index);
            if (HTableItemInfo.getItemPrefixValue() < minSupport) {
                HTableItemInfoList.remove(HTableItemInfo);
            }
        }
    }

    @Deprecated
    private void removeProbabilityDataBelowSupport(List<HTableItemInfo> HTableItemInfoList, double minSupport) {
        for (int index = 0; index < HTableItemInfoList.size(); index++) {
            HTableItemInfo HTableItemInfo = HTableItemInfoList.get(index);
            if (HTableItemInfo.getItemProbabilityValue() < minSupport) {
                HTableItemInfoList.remove(HTableItemInfo);
            }
        }
    }

    @Deprecated
    private UNode constructConditionalTreeOld(UNode root, String id) {
        int count = root.getChildNodeList().size();
        for (int index = 0; index < count; index++) {
            UNode child = root.getChildNodeList().get(index);
            UNode foundNode = constructConditionalTreeNotRemovingChild(child, id);
            if (foundNode == null) {
                root.removeChildNode(child);
                index--;
                count--;
            }
        }
        return root;
    }


    private UNode constructConditionalTreeNotRemovingChild(UNode node, String id) {
        if (node.getId().equalsIgnoreCase(id)) {
            node.setChildNodeList(new ArrayList<UNode>());
            node.getParentNode().setMiningProbability(node.getNodePrefixValue());
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

    private void printBeforeMining(UncertainTree uncertainTree) {

        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, "Tree For Mining");
        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, uncertainTree.getTraversedString());
        Utils.log(Constant.MULTI_STAR);
        Utils.log(TAG, "Mining Started");
        Utils.log(Constant.MULTI_STAR);
    }
}
