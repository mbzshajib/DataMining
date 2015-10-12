package com.mbzshajib.mining.dataset.uncertain.v3;

import com.mbzshajib.utility.callbacks.SaverOutput;
import com.mbzshajib.utility.model.Output;

import java.util.List;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/12/2015
 * @time: 12:53 PM
 * ****************************************************************
 */


public class RandomGeneratorOutputV3 implements Output {
    SaverOutput saverOutput;
    private List<Double> randoms;

    public SaverOutput getSaverOutput() {
        return saverOutput;
    }

    public void setSaverOutput(SaverOutput saverOutput) {
        this.saverOutput = saverOutput;
    }

    public List<Double> getRandoms() {
        return randoms;
    }

    public void setRandoms(List<Double> randoms) {
        this.randoms = randoms;
    }
}
