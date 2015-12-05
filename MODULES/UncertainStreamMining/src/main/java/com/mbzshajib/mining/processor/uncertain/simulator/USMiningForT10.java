package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.processor.uncertain.callback.WindowCompletionCallBackImpl;
import com.mbzshajib.mining.processor.uncertain.evalutor.Evalutor;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorInput;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorOutput;
import com.mbzshajib.mining.processor.uncertain.model.UncertainTree;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeConstructionOutput;
import com.mbzshajib.mining.processor.uncertain.tree.TreeGenerator;
import com.mbzshajib.utility.common.Constants;
import com.mbzshajib.utility.file.Utility;
import com.mbzshajib.utility.log.Logger;
import com.mbzshajib.utility.model.ProcessingError;

import java.io.*;
import java.util.List;

/**
 * *****************************************************************
 *
 * @author - Md. Badi-Uz-Zaman Shajib
 * @copyright 2015.
 * @email - mbzshajib@gmail.com
 * @gitHub - https://github.com/mbzshajib
 * @date: 11/8/2015
 * @time: 11:46 PM
 * ****************************************************************
 */

public class USMiningForT10 {
    public static String CURR_TIME = Utility.getDateTimeString();

    public static void main(String[] args) throws IOException, ProcessingError {
        simulate();
    }

    public static void simulate() throws IOException, ProcessingError {

        List<MiningInput> miningInputs = MiningInputGenerator.generateMiningInputForT10DataSet();
        Logger.log("Simulation", "simulation started for mining in " + miningInputs.size() + "configurations");
        Logger.log(Constants.MULTI_STAR);
        for (MiningInput miningInput : miningInputs) {
            Logger.log("Simulation ", "Simulation start for configuration" + miningInput.toString());
            TreeConstructionInput treeConstructionInput = getTreeInput(miningInput);
            TreeGenerator processor = new TreeGenerator();
            TreeConstructionOutput treeConstructionOutput = processor.process(treeConstructionInput);
            UncertainTree tree = treeConstructionOutput.getUncertainTree();
            Evalutor evalutor = new Evalutor();
            EvalutorInput evalutorInput = getEvalutorInput(miningInput);

            EvalutorOutput process = evalutor.process(evalutorInput);
            treeConstructionInput.getBufferedReader().close();
            Logger.log(Constants.MULTI_STAR);
        }
    }

    private static EvalutorInput getEvalutorInput(MiningInput miningInput) {
        EvalutorInput input = new EvalutorInput();
        input.setDataSetName("T10");
        input.setResultFileName("result_T10" + CURR_TIME + ".result");
        input.setMetaDataName(miningInput.getMetaDataFile());
        input.setMiningMetaDataPath(miningInput.getMetaDataPath());
        input.setFindFalseNegative(miningInput.isFindFalseNegative());
        input.setMiningDataSetFileName(miningInput.getDataSetName());
        input.setMiningDataSetPath(miningInput.getDataSetPath());
        return input;
    }

    private static TreeConstructionInput getTreeInput(MiningInput miningInput) throws FileNotFoundException {
        final TreeConstructionInput treeConstructionInput = new TreeConstructionInput();
        String inputPath = miningInput.getDataSetPath() + miningInput.getDataSetName();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputPath)));
        treeConstructionInput.setBufferedReader(bufferedReader);
        treeConstructionInput.setFrameSize(miningInput.getFrameSize());
        treeConstructionInput.setWindowSize(miningInput.getWindowSize());
        treeConstructionInput.setWindowCompletionCallback(new WindowCompletionCallBackImpl(miningInput));
        return treeConstructionInput;
    }
}
