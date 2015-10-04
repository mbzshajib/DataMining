package com.mbzshajib.mining.processor.uncertain.model;

import com.mbzshajib.mining.exception.DataNotValidException;
import com.mbzshajib.mining.util.Constant;

import java.util.*;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/21/2015
 * @time: 3:58 PM
 * ****************************************************************
 */


public class HeaderTable {
    private int windowSize;
    private List<HeaderTableItem> headerTableItems;

    public HeaderTable(int windowSize) {
        this.windowSize = windowSize;
        headerTableItems = new ArrayList<HeaderTableItem>();
    }

    public void updateHeaderTable(UNode uNode) throws DataNotValidException {
        HeaderTableItem item = null;
        if ((item = findHeaderTableItemById(uNode.getId())) == null) {
            HeaderTableItem headerTableItem = addNewTableItem(uNode.getId());
            headerTableItem.updateHeaderData(uNode);

        } else {
            item.updateHeaderData(uNode);
        }
    }

    public void slideHeaderTable() {
        for (HeaderTableItem item : headerTableItems) {
            List<UNode> nodes = item.getNodeList();
            int counter = nodes.size();
            for (int i = 0; i < counter; i++) {
                UNode node = nodes.get(i);
                boolean isEmpty = true;
                for (UData data : node.getUncertainDataList()) {
                    if (data.getItemProbability() > 0) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) {
                    nodes.remove(node);
                    counter = counter - 1;
                    i = -1;

                }
            }
        }
    }

    private HeaderTableItem addNewTableItem(String id, int totalNodes) {
        HeaderTableItem headerTableItem = new HeaderTableItem(id, totalNodes);
        headerTableItems.add(headerTableItem);
        return headerTableItem;
    }

    private HeaderTableItem addNewTableItem(String id) {
        HeaderTableItem headerTableItem = new HeaderTableItem(id);
        headerTableItems.add(headerTableItem);
        return headerTableItem;
    }

    private HeaderTableItem findHeaderTableItemById(String id) {
        HeaderTableItem result = getHeaderTableItem(id);
        return result;
    }

    private HeaderTableItem getHeaderTableItem(String id) {
        HeaderTableItem result = null;
        for (HeaderTableItem item : headerTableItems) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                result = item;
            }
        }
        return result;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public String traverse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constant.NEW_LINE);
        for (HeaderTableItem item : headerTableItems) {
            stringBuilder.append(item.traverse());
            stringBuilder.append(Constant.NEW_LINE);
        }
        return stringBuilder.toString();
    }

    public List<HeaderTableItem> getHeaderTableItems() {
        return headerTableItems;
    }


    public List<UNode> getAllNodesOfHeaderTableItem(String id) {
        List<UNode> list = null;
        HeaderTableItem item = findHeaderTableItemById(id);
        if (item != null) {
            list = item.getNodeList();
        } else {
            list = Collections.emptyList();
        }
        return list;

    }

    private Map<String, Integer> getUniqueIds() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (HeaderTableItem item : headerTableItems) {
            result.put(item.getItemId(), item.getNodeList().size());
        }
        return result;
    }

    public int findNode(UNode node) {
        int result = -1;
        for (int i = 0; i < headerTableItems.size(); i++) {
            HeaderTableItem headerTableItem = headerTableItems.get(i);
            int index = -1;
            if ((index = headerTableItem.getNodeIndex(node)) != -1) {
                result = index;
                break;
            }
        }
        return result;
    }

    public HeaderTable copy() {
        HeaderTable headerTable = new HeaderTable(windowSize);
        Map<String, Integer> ids = getUniqueIds();
        Iterator iterator = ids.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            String id = (String) pair.getKey();
            int totalNode = (int) pair.getValue();
            headerTable.addNewTableItem(id, totalNode);
        }
        return headerTable;
    }

    void addNode(UNode node, int index) {
        HeaderTableItem item = findHeaderTableItemById(node.getId());
        item.addNodeItem(node, index);
    }

    public List<HTableItemInfo> getHeaderItemInfo() {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            HTableItemInfo HTableItemInfo = new HTableItemInfo();
            HTableItemInfo.setItemId(item.getItemId());
            HTableItemInfo.setItemProbabilityValue(item.getItemProbabilityValue());
            HTableItemInfo.setItemPrefixValue(item.getItemPrefixValue());
            result.add(HTableItemInfo);
        }
        return result;
    }

    public void updateHeaderTableFromDistinctList(List<UNode> distinctList) throws DataNotValidException {
        for (UNode node : distinctList) {
            updateHeaderTable(node);
        }
    }

    public List<HTableItemInfo> getInFrequentItemInfoByPrefix(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (prefixVal < minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

    public List<HTableItemInfo> getFrequentItemInfoByPrefix(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (prefixVal >= minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

    public List<HTableItemInfo> getFrequentItemInfoByMiningValue(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (miningValue >= minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

    public List<HTableItemInfo> getInFrequentItemInfoBySupport(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (probabilityValue < minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

    public List<HTableItemInfo> getFrequentItemInfoBySupport(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (probabilityValue >= minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

    public List<HTableItemInfo> getInFrequentItemInfoByMiningValue(double minSupport) {
        List<HTableItemInfo> result = new ArrayList<HTableItemInfo>();
        for (HeaderTableItem item : headerTableItems) {
            double prefixVal = item.getItemPrefixValue();
            double probabilityValue = item.getItemProbabilityValue();
            double miningValue = item.getMiningValue();
            if (miningValue < minSupport) {
                HTableItemInfo hTableItemInfo = new HTableItemInfo();
                hTableItemInfo.setItemId(item.getItemId());
                hTableItemInfo.setItemPrefixValue(prefixVal);
                hTableItemInfo.setItemProbabilityValue(probabilityValue);
                hTableItemInfo.setMiningProbability(miningValue);
                result.add(hTableItemInfo);
            }
        }
        return result;
    }

}
