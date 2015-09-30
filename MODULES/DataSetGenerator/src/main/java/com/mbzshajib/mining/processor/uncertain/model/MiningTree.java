package com.mbzshajib.mining.processor.uncertain.model;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/30/2015
 * time: 5:11 PM
 * ****************************************************************
 */

public class MiningTree {
    private MiningNode rootNode;

    public MiningTree() {
        rootNode = new MiningNode("0");
        rootNode.setParent(null);

    }

    public MiningNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(MiningNode rootNode) {
        this.rootNode = rootNode;
    }

    public void addNewNode(MiningNode child, MiningNode parent) {
        parent.addChild(child);
    }

    public void addParent(MiningNode child, MiningNode parent) {
        child.setParent(parent);
        parent.addChild(child);
    }

}
