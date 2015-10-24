package com.mbzshajib.mining.processor.uncertain.model;

import java.util.*;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/20/2015
 * @time: 11:30 PM
 * ****************************************************************
 */

public class SufHeader {
    private List<SufHItem> itemList;

    public SufHeader() {
        itemList = new ArrayList<SufHItem>();
    }

    public List<SufHItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SufHItem> itemList) {
        this.itemList = itemList;
    }

    public void addNewNodeToHeader(SufNode node) {
        SufHItem item = getHItem(node.getId());
        if (item == null) {
            item = new SufHItem(node.getId());
            item.getNodeList().add(node);
            itemList.add(item);
            item.setTotalSupport(node.getProbability());
        } else {
            item.getNodeList().add(node);
            item.updateSupport(node.getProbability());
        }
    }

    public void updateHeaderItem(SufNode node) {
        SufHItem hItem = getHItem(node.getId());
        hItem.updateSupport(node.getProbability());
    }


    private SufHItem getHItem(String id) {
        SufHItem result = null;
        for (SufHItem item : itemList) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                result = item;
            }
        }
        return result;
    }

    public void removeInfrequentItems(double minSupport) {
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            SufHItem item = itemList.get(i);
            if (item.getTotalSupport() < minSupport) {
                item.removeNodesFromTree();
                itemList.remove(item);
                i--;
                size--;
            }
        }
    }

    public void removeInfrequentItemsForMining(double minSupport) {
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            SufHItem item = itemList.get(i);
            if (item.getMiningSupport() < minSupport) {
                item.removeNodesFromTree();
                itemList.remove(item);
                i--;
                size--;
            }
        }
    }

    public void removeItemById(String id) {
        SufHItem hItem = getHItem(id);
        if (hItem != null) {
            hItem.removeNodesFromTree();
            itemList.remove(hItem);
        }
    }

    public void sortByProbabilityValueDesc() {
        Collections.sort(itemList, new Comparator<SufHItem>() {
            @Override
            public int compare(SufHItem o1, SufHItem o2) {
                if (o1.getTotalSupport() < o2.getTotalSupport()) {
                    return -1;
                }
                if (o1.getTotalSupport() > o2.getTotalSupport()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public void sortByMiningValueDesc() {
        Collections.sort(itemList, new Comparator<SufHItem>() {
            @Override
            public int compare(SufHItem o1, SufHItem o2) {
                if (o1.getMiningSupport() < o2.getMiningSupport()) {
                    return -1;
                }
                if (o1.getMiningSupport() > o2.getMiningSupport()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public void updateFromAllNodes(List<SufNode> distinctList) {
        for (SufNode node : distinctList) {
            SufHItem item = getHItem(node.getId());
            if (item == null) {
                item = new SufHItem(node.getId());
                item.setTotalSupport(node.getProbability() * node.getTotalCount());
                this.itemList.add(item);
            } else {
                item.updateSupport(node.getProbability() * node.getTotalCount());
            }
            item.getNodeList().add(node);

        }
    }

    public void updateFromAllNodesForMining(List<SufNode> distinctList) {
        for (SufNode node : distinctList) {
            SufHItem item = getHItem(node.getId());
            if (item == null) {
                item = new SufHItem(node.getId());
                item.setMiningSupport(node.getMiningProbability());
                this.itemList.add(item);
            } else {
                item.updateMiningSupport(node.getMiningProbability());
            }
            item.getNodeList().add(node);

        }
    }
}
