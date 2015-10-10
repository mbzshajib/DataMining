package com.mbzshajib.mining.dataset.uncertain.v2;

import com.mbzshajib.utility.callbacks.SaverOutput;
import com.mbzshajib.utility.model.Output;

import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 6:41 PM
 * ****************************************************************
 */


public class RandomGeneratorOutputV2 implements Output {
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

    @Override
    public String toString() {

        int size = 0;
        if (randoms != null) {
            size = randoms.size();
        }

        return "RandomGeneratorOutputV2{" +
                "saverOutput=" + saverOutput +
                ", total randoms=" + size +
                '}';
    }
}
