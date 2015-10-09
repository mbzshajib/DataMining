package com.mbzshajib.mining.exception;

/**
 * *****************************************************************
 * Copyright  2015.
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email  - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 9/20/2015
 * @time: 11:23 PM
 * ****************************************************************
 */

public class DataNotValidException extends Throwable {
    public DataNotValidException(String message) {
        super(message);
    }

    public DataNotValidException(Throwable throwable) {
        super(throwable);
    }
}
