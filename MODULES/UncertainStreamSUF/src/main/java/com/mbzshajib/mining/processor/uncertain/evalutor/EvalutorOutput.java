package com.mbzshajib.mining.processor.uncertain.evalutor;

import com.mbzshajib.utility.model.Output;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 10/11/2015
 * @time: 3:16 AM
 * ****************************************************************
 */

public class EvalutorOutput implements Output {
    private String resultPath;
    private String resultFileName;

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    @Override
    public String toString() {
        return "EvalutorOutput{" +
                "resultPath='" + resultPath + '\'' +
                ", resultFileName='" + resultFileName + '\'' +
                '}';
    }
}
