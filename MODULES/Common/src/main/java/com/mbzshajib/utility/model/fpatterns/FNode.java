package com.mbzshajib.utility.model.fpatterns;

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
 * @time: 12:28 PM
 * ****************************************************************
 */


public class FNode {
    private static final String TAB = "\t";
    private static final String NEW_LINE = "\n";
    private String id;
    private List<FNode> childes;
    private FNode parent;
    private int depth;

    public FNode(String id, int depth) {
        this.id = id;
        this.depth = depth;
        childes = new ArrayList<FNode>();
    }

    public FNode() {
        this.id = "0";
        this.depth = 0;
        childes = new ArrayList<FNode>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FNode> getChildes() {
        return childes;
    }

    public void setChildes(List<FNode> childes) {
        this.childes = childes;
    }

    public FNode getParent() {
        return parent;
    }

    public void setParent(FNode parent) {
        this.parent = parent;
    }

    public FNode addChild(FNode child) {
        child.setParent(this);
        childes.add(child);
        return child;
    }

    public void addChildesChain(List<FNode> childesChain) {
        FNode parentPointer = this;
        for (FNode node : childesChain) {
            parentPointer = parentPointer.addChild(node);
        }
    }

    public void addChildesChain(String[] childesChain) {
        FNode parentPointer = this;
        for (String nodeId : childesChain) {
            int index = findInChild(nodeId, parentPointer);
            if (index == -1) {
                FNode node = new FNode(nodeId, (parentPointer.getDepth() + 1));
                parentPointer = parentPointer.addChild(node);
            } else {
                parentPointer = parentPointer.getChildes().get(index);
            }

        }
    }

    private int findInChild(String nodeId, FNode parentPointer) {
        int result = -1;
        for (int i = 0; i < parentPointer.getChildes().size(); i++) {
            FNode node = parentPointer.getChildes().get(i);
            if (node.getId().equalsIgnoreCase(nodeId)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("P-").append(id).append(TAB);
        stringBuilder.append("[");
        for (FNode child : childes) {
            stringBuilder.append("C(").append(child.getId()).append(",").append(child.getDepth()).append(")");
        }
        stringBuilder.append("]").append(NEW_LINE);
        for (FNode child : childes) {
            stringBuilder.append(child.traverse());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FNode{").append("id='").append(id).append('\'').append(", childes=").append(childes).append(", parent=").append(", depth=").append(depth).append('}').toString();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void getLeafNodes(List<FNode> leafList) {
        if (childes == null || childes.size() == 0) {
            leafList.add(this);
            return;
        } else {
            for (FNode node : childes) {
                node.getLeafNodes(leafList);
            }
        }
    }

    public List<FNode> getAllNodesFromRoot() {
        List<FNode> result = new ArrayList<FNode>();
        FNode pointer = this;
        while (true) {
            if (pointer == null || pointer.getId().equals("0")) {
                break;
            } else {
                result.add(pointer);
                pointer = pointer.getParent();
            }
        }
        return result;

    }

    public List<String[]> getAllFrequentItems() {
        List<String[]> result = new ArrayList<String[]>();
        List<FNode> leafNodes = new ArrayList<FNode>();
        this.getLeafNodes(leafNodes);
        for (FNode leaf : leafNodes) {
            FNode tmp = leaf;
            while (tmp.getDepth() != 0) {
                List<FNode> allNodesFromRoot = tmp.getAllNodesFromRoot();
                String[] branchNodeString = new String[allNodesFromRoot.size()];
                for (int i = 0; i < allNodesFromRoot.size(); i++) {
                    branchNodeString[i] = allNodesFromRoot.get(i).getId();
                }
                result.add(branchNodeString);
                tmp = tmp.getParent();
            }
        }
        return result;
    }
}
