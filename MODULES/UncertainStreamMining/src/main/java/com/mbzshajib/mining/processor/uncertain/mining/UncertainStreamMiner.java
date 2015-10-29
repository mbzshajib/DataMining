//package com.mbzshajib.mining.processor.uncertain.mining;
//
//import com.mbzshajib.mining.processor.uncertain.model.*;
//import com.mbzshajib.utility.common.Constants;
//import com.mbzshajib.utility.exception.DataNotFoundException;
//import com.mbzshajib.utility.log.Logger;
//import com.mbzshajib.utility.model.ProcessingError;
//import com.mbzshajib.utility.model.Processor;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
///**
// * *****************************************************************
// * Copyright  2015.
// *
// * @author - Md. Badi-Uz-Zaman Shajib
// * @email - mbzshajib@gmail.com
// * @gitHub - https://github.com/mbzshajib
// * @date: 9/28/2015
// * @time: 7:46 PM
// * ****************************************************************
// */
//
//public class UncertainStreamMiner implements Processor<UncertainStreamMineInput, UncertainStreamMineOutput> {
//    public static final String TAG = UncertainStreamMiner.class.getCanonicalName();
//    private List<FrequentItem> frequentItemList;
//
//    @Override
//    public UncertainStreamMineOutput process(UncertainStreamMineInput uncertainStreamMineInput) throws ProcessingError {
//        long startTime = System.currentTimeMillis();
//        frequentItemList = new ArrayList<FrequentItem>();
//        UncertainTree uncertainTree = uncertainStreamMineInput.getUncertainTree();
//        double minSupport = uncertainStreamMineInput.getMinSupport();
//        try {
//            startMining(uncertainTree, minSupport);
//        } catch (DataNotFoundException e) {
//            throw new ProcessingError(e);
//        }
//        UncertainStreamMineOutput output = new UncertainStreamMineOutput();
//        output.setFrequentItemList(this.frequentItemList);
//        long endTime = System.currentTimeMillis();
//
//        TimeModel miningTime = new TimeModel(startTime, endTime);
//        output.setMiningTime(miningTime);
//        return output;
//    }
//
//    private void startMining(UncertainTree uncertainTree, double minSupport) throws DataNotFoundException {
//        UNode rootNode = uncertainTree.getRootNode();
//        HeaderTable headerTable = uncertainTree.getHeaderTable();
//        int windowSize = headerTable.getWindowSize();
//        List<UNode> infrequentByPrefix = headerTable.removeAndFindInfrequentNodesByPrefix(minSupport);
//
//        for (int i = 0; i < infrequentByPrefix.size(); i++) {
//            UNode tmpNode = infrequentByPrefix.get(i);
//            UNode parentNode = tmpNode.getParentNode();
//            parentNode.removeChildNode(tmpNode);
//        }
//        headerTable.sortByPrefix();
//        List<HTableItemInfo> listToBeMined = headerTable.getHeaderInfo();
//        for (int count = listToBeMined.size() - 1; count >= 0; count--) {
//            UNode tmpNode = rootNode.copy();
//            FrequentItem frequentItem = new FrequentItem();
//            String id = listToBeMined.get(count).getItemId();
//            frequentItem.addFrequentItem(id);
//            frequentItemList.add(frequentItem);
//            mine(tmpNode, headerTable, frequentItem, windowSize, id, minSupport, false);
//        }
//
//
//    }
//
//    private void mine(UNode root, HeaderTable headerTable, FrequentItem item, int windowSize, String id, double minSupport, boolean isFirstIteration) throws DataNotFoundException {
//        item.addFrequentItem(id);
//        if (isFirstIteration) {
//            frequentItemList.add(item);
//        }
//        item = new FrequentItem(item);
//        constructConditionalTreeNotRemovingChild(root, id);
//        headerTable = headerTable.copy(root);
//        headerTable.removeAllNodes(id);
//        List<UNode> leafNodeList = new ArrayList<UNode>();
//        root.getLeafNodeList(leafNodeList);
//        updateMiningProbability(leafNodeList);
//        List<UNode> infrequentNodes = headerTable.removeAndFindInfrequentNodesByMining(minSupport);
//        for (int i = 0; i < infrequentNodes.size(); i++) {
//            UNode tmpNode = infrequentNodes.get(i);
//            UNode parentNode = tmpNode.getParentNode();
//            parentNode.removeChildNode(tmpNode);
//        }
//        updateFrequentItem(root, item);
//        List<HTableItemInfo> headerItemInfo = headerTable.getHeaderInfo();
//        for (int i = headerItemInfo.size() - 1; i > 0; i--) {
//            item = new FrequentItem(item);
//            mine(root, headerTable, item, windowSize, headerItemInfo.get(i).getItemId(), minSupport, false);
//        }
//    }
//
//    private void updateMiningProbability(List<UNode> leafNodeList) {
//        for (UNode leafNode : leafNodeList) {
//            updateParentMiningData(leafNode);
//        }
//
//    }
//
//    private void updateParentMiningData(UNode leafNode) {
//        UNode parentNode = leafNode.getParentNode();
//        if (leafNode.getId().equals("0") || parentNode.getId().equals("0")) {
//            return;
//        } else {
//            parentNode.setMiningProbability(parentNode.getMiningProbability() + leafNode.getMiningProbability());
//            updateParentMiningData(parentNode);
//        }
//    }
//
//    private void updateFrequentItem(UNode rootNode, FrequentItem item) {
//
//        for (int i = 0; i < rootNode.getChildNodeList().size(); i++) {
//            UNode child = rootNode.getChildNodeList().get(i);
//            FrequentItem tmpItem = new FrequentItem(item);
//            tmpItem.addFrequentItem(child.getId());
//            frequentItemList.add(tmpItem);
//            updateFrequentItem(child, item);
//        }
//    }
//
//    private void removeInfrequentItemNodes(UNode rootNode, String itemId) {
//        rootNode.removeNodeIfChildOfAnyDepthById(itemId);
//    }
//
//    private void sortByID(List<HTableItemInfo> sortedFilteredById) {
//        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
//            @Override
//            public int compare(HTableItemInfo one, HTableItemInfo two) {
//                int idOne = Integer.parseInt(one.getItemId());
//                int idTwo = Integer.parseInt(two.getItemId());
//                int diff = idTwo - idOne;
//                return diff;
//            }
//        });
//
//    }
//
//    private void sortByPrefix(List<HTableItemInfo> sortedFilteredById) {
//        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
//            @Override
//            public int compare(HTableItemInfo one, HTableItemInfo two) {
//                double diff = two.getItemPrefixValue() - one.getItemPrefixValue();
//                if (diff == 0) {
//                    return 0;
//                } else if (diff > 0) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            }
//        });
//
//    }
//
//    private void sortByProbability(List<HTableItemInfo> sortedFilteredById) {
//        Collections.sort(sortedFilteredById, new Comparator<HTableItemInfo>() {
//            @Override
//            public int compare(HTableItemInfo one, HTableItemInfo two) {
//                double diff = two.getItemProbabilityValue() - one.getItemProbabilityValue();
//                if (diff == 0) {
//                    return 0;
//                } else if (diff > 0) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            }
//        });
//
//    }
//
//    @Deprecated
//    private UNode constructConditionalTreeRemovingChild(UNode node, String id) {
//        if (node.getId().equalsIgnoreCase(id)) {
//            UNode parentNode = node.getParentNode();
//            parentNode.removeChildNode(node);
//            return parentNode;
//        } else {
//            if (node.getChildNodeList() == null || node.getChildNodeList().isEmpty()) {
//                return null;
//            } else {
//                UNode returnNode = null;
//                int count = node.getChildNodeList().size();
//                for (int index = 0; index < count; index++) {
//                    UNode child = node.getChildNodeList().get(index);
//                    UNode foundNode = constructConditionalTreeRemovingChild(child, id);
//                    if (foundNode == null) {
//                        node.removeChildNode(child);
//                        index--;
//                        count--;
//                    } else {
//                        returnNode = foundNode;
//                    }
//                }
//                return returnNode;
//            }
//        }
//    }
//
//    @Deprecated
//    private void removeDataBelowSupport(double minSupport, List<HTableItemInfo> HTableItemInfoList) {
//        removePrefixDataBelowSupport(HTableItemInfoList, minSupport);
//        removeProbabilityDataBelowSupport(HTableItemInfoList, minSupport);
//    }
//
//    @Deprecated
//    private void removePrefixDataBelowSupport(List<HTableItemInfo> HTableItemInfoList, double minSupport) {
//        for (int index = 0; index < HTableItemInfoList.size(); index++) {
//            HTableItemInfo HTableItemInfo = HTableItemInfoList.get(index);
//            if (HTableItemInfo.getItemPrefixValue() < minSupport) {
//                HTableItemInfoList.remove(HTableItemInfo);
//            }
//        }
//    }
//
//    @Deprecated
//    private void removeProbabilityDataBelowSupport(List<HTableItemInfo> HTableItemInfoList, double minSupport) {
//        for (int index = 0; index < HTableItemInfoList.size(); index++) {
//            HTableItemInfo HTableItemInfo = HTableItemInfoList.get(index);
//            if (HTableItemInfo.getItemProbabilityValue() < minSupport) {
//                HTableItemInfoList.remove(HTableItemInfo);
//            }
//        }
//    }
//
//    @Deprecated
//    private UNode constructConditionalTreeOld(UNode root, String id) {
//        int count = root.getChildNodeList().size();
//        for (int index = 0; index < count; index++) {
//            UNode child = root.getChildNodeList().get(index);
//            UNode foundNode = constructConditionalTreeNotRemovingChild(child, id);
//            if (foundNode == null) {
//                root.removeChildNode(child);
//                index--;
//                count--;
//            }
//        }
//        return root;
//    }
//
//
//    private UNode constructConditionalTreeNotRemovingChild(UNode node, String id) {
//        if (node.getId().equalsIgnoreCase(id)) {
//            node.setChildNodeList(new ArrayList<UNode>());
//            node.getParentNode().setMiningProbability(node.getNodePrefixValue());
//            return node;
//        } else {
//            if (node.getChildNodeList() == null || node.getChildNodeList().isEmpty()) {
//                return null;
//            } else {
//                UNode returnNode = null;
//                int count = node.getChildNodeList().size();
//                for (int index = 0; index < count; index++) {
//                    UNode child = node.getChildNodeList().get(index);
//                    UNode foundNode = constructConditionalTreeNotRemovingChild(child, id);
//                    if (foundNode == null) {
//                        node.removeChildNode(child);
//                        index--;
//                        count--;
//                    } else {
//                        returnNode = foundNode;
//                    }
//                }
//                return returnNode;
//            }
//        }
//    }
//
//    private void printBeforeMining(UncertainTree uncertainTree) {
//
//        Logger.log(Constants.MULTI_STAR);
//        Logger.log(TAG, "Tree For Mining");
//        Logger.log(Constants.MULTI_STAR);
//        Logger.log(TAG, uncertainTree.getTraversedString());
//        Logger.log(Constants.MULTI_STAR);
//        Logger.log(TAG, "Mining Started");
//        Logger.log(Constants.MULTI_STAR);
//    }
//}