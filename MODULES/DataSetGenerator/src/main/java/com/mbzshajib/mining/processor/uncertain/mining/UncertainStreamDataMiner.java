package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.*;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/28/2015
 * time: 7:46 PM
 * ****************************************************************
 */

public class UncertainStreamDataMiner implements Processor<UncertainStreamDataMinerInput, UncertainStreamDataMinerOutput> {
    @Override
    public UncertainStreamDataMinerOutput process(UncertainStreamDataMinerInput uncertainStreamDataMinerInput) throws ProcessingError {
        HeaderTable headerTable = uncertainStreamDataMinerInput.getUncertainTree().getHeaderTable();
        List<HeaderInfo> headerInfoList = headerTable.getNotEmptyItems();
        sortHeaderInfoByPrefixValue(headerInfoList);
        UNode rootNode = uncertainStreamDataMinerInput.getUncertainTree().getRootNode();
        MiningTree miningTree = getMiningTreeFromUncertainTree(uncertainStreamDataMinerInput.getUncertainTree());
        return null;
    }

    private MiningTree getMiningTreeFromUncertainTree(UncertainTree uncertainTree) {
        MiningTree miningTree = new MiningTree();
        MiningNode miningRoot = miningTree.getRootNode();
        UNode uncertainRoot = uncertainTree.getRootNode();
        List<UNode> uncertainChile = uncertainRoot.getChildNodeList();
        List<MiningNode> list = new ArrayList<MiningNode>();
        list.add(miningRoot);
        mergeAndAddToNewTree(list, uncertainChile);

        return null;
    }

    private List<MiningNode> mergeAndAddToNewTree(List<MiningNode> miningNodes, List<UNode> childNodeList) {
        List<MiningNode> miningChildList = new ArrayList<MiningNode>();
        sortUNodeById(childNodeList);
        for(UNode node:childNodeList){
            int foundedIndex = -1;
            for(int i =0;i<miningChildList.size();i++){
                if(miningChildList.get(i).getId().equals(node.getId())){
                    foundedIndex = i;
                    break;
                }
            }
            if(foundedIndex ==-1){
                MiningNode miningNode = new MiningNode(node.getId());
//                miningNode.set
            }

        }
        return miningChildList;
    }

    private void sortUNodeById(List<UNode> childNodeList) {
        Collections.sort(childNodeList, new Comparator<UNode>() {
            @Override
            public int compare(UNode nodeOne, UNode nodeTow) {
                int nodeOneId = Integer.parseInt(nodeOne.getId());
                int nodeTwoId = Integer.parseInt(nodeTow.getId());
                return (nodeTwoId - nodeOneId);
            }
        });
    }

    private void sortHeaderInfoByPrefixValue(List<HeaderInfo> headerInfoList) {
        Collections.sort(headerInfoList, new Comparator<HeaderInfo>() {
            @Override
            public int compare(HeaderInfo headerInfoOne, HeaderInfo headerInfoTwo) {
                double compare = headerInfoTwo.getPrefixValue() - headerInfoOne.getPrefixValue();
                if (compare == 0) {
                    return 0;
                } else if (compare > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    private void sortHeaderInfoBySupport(List<HeaderInfo> headerInfoList) {
        Collections.sort(headerInfoList, new Comparator<HeaderInfo>() {
            @Override
            public int compare(HeaderInfo headerInfoOne, HeaderInfo headerInfoTwo) {
                double compare = headerInfoTwo.getSupportValue() - headerInfoOne.getSupportValue();
                if (compare == 0) {
                    return 0;
                } else if (compare > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
