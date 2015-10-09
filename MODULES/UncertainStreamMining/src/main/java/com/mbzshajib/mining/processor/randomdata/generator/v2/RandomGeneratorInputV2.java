package com.mbzshajib.mining.processor.randomdata.generator.v2;

import com.mbzshajib.utility.model.Input;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:41 PM
 * ****************************************************************
 */


public class RandomGeneratorInputV2 implements Input {
    private List<RInputInfo> list;

    public List<RInputInfo> getList() {
        return list;
    }

    public void setList(List<RInputInfo> list) {
        this.list = list;
    }
}
