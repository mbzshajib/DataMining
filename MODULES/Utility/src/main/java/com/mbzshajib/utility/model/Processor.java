package com.mbzshajib.utility.model;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/4/2015
 * @time: 4:49 PM
 * ****************************************************************
 */


public interface Processor<I extends Input, O extends Output> {
    O process(I i) throws ProcessingError;
}
