package com.mbzshajib.mining.processor.uncertain.mining;

import com.mbzshajib.mining.processor.uncertain.model.FrequentItem;
import com.mbzshajib.mining.processor.uncertain.model.UHeader;
import com.mbzshajib.mining.processor.uncertain.model.UNode;
import com.mbzshajib.utility.model.ProcessingError;
import com.mbzshajib.utility.model.Processor;
import com.mbzshajib.utility.model.fpatterns.FNode;

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
        double minSup = uncertainStreamMineInput.getMinSupport();
        UNode rootNode = uncertainStreamMineInput.getUncertainTree().getRootNode();
        System.out.println(rootNode.traverse());
        System.out.println("Minimum Support = " + minSup);
        List<UNode> distinctNodes = rootNode.getDistinctNodes();
        UHeader header = new UHeader();
        for (UNode distinct : distinctNodes) {
            header.addNodeToHeader(distinct);
        }
        header.sortBySupportDsc();
        System.out.println(header.traverse());

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
        //TODO: Create Header With Mining Probability Only
        //TODO: Remove Item Less Than min_sup by mining Value
        //TODO: ADD PATTERNS TO ROOT_F_PATTERNS
        //TODO: LOOP(EACH ITEM IN HEADER)
        //  TODO: COPY ROOT
        //  TODO: REMOVE ITEM FROM COPY TREE
        //  TODO: ADD ITEM TO EXISTING frequentItem
        //  TODO: MINE(COPY, minsup, created frequentItem).
    }


}
