package com.mbzshajib.utility.model;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:14 PM
 * ****************************************************************
 */

public class ProcessingError extends Throwable {
    public ProcessingError(Throwable throwable) {
        super(throwable);
    }
}
