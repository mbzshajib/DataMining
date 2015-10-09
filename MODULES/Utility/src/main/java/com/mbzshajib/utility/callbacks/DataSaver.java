package com.mbzshajib.utility.callbacks;

import com.mbzshajib.utility.model.ProcessingError;

/**
 * *****************************************************************
 * Copyright  2015.
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/9/2015
 * @time: 9:20 AM
 * ****************************************************************
 */


public interface DataSaver {
    SaverInput prepareSaverInput(Object... input);

    SaverOutput save(SaverInput input) throws ProcessingError;
}
