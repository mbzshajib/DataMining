package com.mbzshajib.mining.processor.uncertain.patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 * All Rights Reserved to
 * Kona Software Lab Ltd.
 * Redistribution or Using any part of source code or binary
 * can not be done without permission of Kona Software Lab Ltd.
 * *****************************************************************
 *
 * @author: Md. Badi-Uz-Zaman Shajib
 * @email: shajib_zaman@konasl.com
 * @date: 10/22/2015
 * @time: 12:44 PM
 * ****************************************************************
 */


public class FNodeTest {
    public static void main(String[] args) {
        FNode rootNode = new FNode("null", 0);
        String[] branch1 = new String[]{"1", "2", "3", "4"};
        String[] branch2 = new String[]{"1", "2", "3"};
        String[] branch3 = new String[]{"1", "2", "3", "4"};
        String[] branch4 = new String[]{"2", "3", "4"};
        String[] branch5 = new String[]{"2"};
        String[] branch6 = new String[]{"1", "4"};
        String[] branch7 = new String[]{"1", "2", "4"};
        String[] branch8 = new String[]{"1", "3"};
        String[] branch9 = new String[]{"3"};
        rootNode.addChildesChain(branch1);
        rootNode.addChildesChain(branch2);
        rootNode.addChildesChain(branch3);
        rootNode.addChildesChain(branch4);
        rootNode.addChildesChain(branch5);
        rootNode.addChildesChain(branch6);
        rootNode.addChildesChain(branch7);
        rootNode.addChildesChain(branch8);
        rootNode.addChildesChain(branch9);
        List<FNode> leafList = new ArrayList<FNode>();
        rootNode.getLeafNodes(leafList);
        FNode node = leafList.get(4);
        List<FNode> allNodesFromRoot = node.getAllNodesFromRoot();
        System.out.println(node.traverse());

    }
}
