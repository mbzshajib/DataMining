package com.mbzshajib.mining.processor.uncertain.infrequent;

import com.mbzshajib.mining.processor.uncertain.model.UInputData;
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
 * @date: 11/1/2015
 * @time: 6:37 PM
 * ****************************************************************
 */

public class InfrequentRemoverProcessor implements Processor<InfrequentRemovarInput, InfrepuentRemoverOutput> {
    @Override
    public InfrepuentRemoverOutput process(InfrequentRemovarInput infrequentRemovarInput) throws ProcessingError {
        List<List<UInputData>> transactionList = copy(infrequentRemovarInput.getTransactionList());
        FNode node = infrequentRemovarInput.getNode();
        List<FNode> childes = node.getChildes();
        //TODO later sort.
        for (List<UInputData> transaction : transactionList) {
            removeInfrequentItemFromTransaction(transaction, childes);
        }

        System.out.println();


        return null;
    }

    private void removeInfrequentItemFromTransaction(List<UInputData> transaction, List<FNode> childes) {
        int count = transaction.size();
        for (int i = 0; i < count; i++) {
            UInputData data = transaction.get(i);
            boolean isExists = isExistsInFrequent(childes, data);

            if (!isExists) {
                transaction.remove(data);
                i--;
                count--;
            }

        }
    }

    private boolean isExistsInFrequent(List<FNode> childes, UInputData data) {
        boolean result = false;
        for (FNode node : childes) {
            if (node.getId().equals(data.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private List<List<UInputData>> copy(List<List<UInputData>> transactionList) {
        List<List<UInputData>> result = new ArrayList<List<UInputData>>();
        for (List<UInputData> transaction : transactionList) {
            List<UInputData> newList = new ArrayList<UInputData>();
            for (UInputData item : transaction) {
                newList.add(item);
            }
            result.add(newList);
        }
        return result;
    }
}
