package com.mbzshajib.mining.processor.uncertain.infrequent;

import com.mbzshajib.mining.processor.uncertain.model.TimeModel;
import com.mbzshajib.utility.model.Output;
import com.mbzshajib.utility.model.fpatterns.FNode;

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

public class InfrepuentRemoverOutput implements Output {
    private FNode node;
    private TimeModel timeModel;

    public FNode getNode() {
        return node;
    }

    public void setNode(FNode node) {
        this.node = node;
    }

    public TimeModel getTimeModel() {
        return timeModel;
    }

    public void setTimeModel(TimeModel timeModel) {
        this.timeModel = timeModel;
    }
}
