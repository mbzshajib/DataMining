package com.mbzshajib.utility.model;

/**
 * *****************************************************************
 * Copyright  2015.
 * Author - Md. Badi-Uz-Zaman Shajib
 * Email  - mbzshajib@gmail.com
 * GitHub - https://github.com/mbzshajib
 * date: 9/4/2015
 * time: 4:49 PM
 * ****************************************************************
 */


public interface Processor<I extends Input, O extends Output> {
    O process(I i);
}
