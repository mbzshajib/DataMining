package com.mbzshajib.mining.processor.uncertain.simulator;

import com.mbzshajib.mining.processor.uncertain.callback.SufWindowCompletionCallback;
import com.mbzshajib.mining.processor.uncertain.evalutor.Evalutor;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorInput;
import com.mbzshajib.mining.processor.uncertain.evalutor.EvalutorOutput;
import com.mbzshajib.mining.processor.uncertain.model.SufTree;
import com.mbzshajib.mining.processor.uncertain.tree.SufTreeConstructionInput;
import com.mbzshajib.mining.processor.uncertain.tree.SufTreeConstructorOutput;
import com.mbzshajib.mining.processor.uncertain.tree.SufTreeGenerator;
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
 * @date: 10/15/2015
 * @time: 12:51 AM
 * ****************************************************************
 */

public class SufMiningForMushroom {
    public static String CURR_TIME = Utility.getDateTimeString();

    public static void main(String[] args) throws IOException, ProcessingError {
        simulate();
    }

    public static void simulate() throws IOException, ProcessingError {

        List<MiningInput> miningInputs = MiningInputGenerator.generateMiningInputForMashroomDataSet();
        Logger.log("Simulation", "simulation started for mining in " + miningInputs.size() + " configurations");
        Logger.log(Constants.MULTI_STAR);
        for (MiningInput miningInput : miningInputs) {
            Logger.log("Simulation ", "Simulation start for configuration" + miningInput.toString());
            SufTreeConstructionInput treeConstructionInput = getTreeInput(miningInput);
            SufTreeGenerator processor = new SufTreeGenerator();
            SufTreeConstructorOutput treeConstructionOutput = processor.process(treeConstructionInput);
            SufTree tree = treeConstructionOutput.getSufTree();
            Evalutor evalutor = new Evalutor();
            EvalutorInput evalutorInput = getEvalutorInput(miningInput);

            EvalutorOutput process = evalutor.process(evalutorInput);
            treeConstructionInput.getBufferedReader().close();
            Logger.log(Constants.MULTI_STAR);
        }
    }

    private static EvalutorInput getEvalutorInput(MiningInput miningInput) {
        EvalutorInput input = new EvalutorInput();
        input.setDataSetName("Mushroom");
        input.setResultFileName("Mushroom" + CURR_TIME + ".result");
        input.setMetaDataName(miningInput.getMetaDataFile());
        input.setMiningMetaDataPath(miningInput.getMetaDataPath());
        input.setFindFalseNegative(miningInput.isFindFalseNegative());
        input.setMiningDataSetFileName(miningInput.getDataSetName());
        input.setMiningDataSetPath(miningInput.getDataSetPath());
        return input;
    }

    private static SufTreeConstructionInput getTreeInput(MiningInput miningInput) throws FileNotFoundException {
        final SufTreeConstructionInput treeConstructionInput = new SufTreeConstructionInput();
        String inputPath = miningInput.getDataSetPath() + miningInput.getDataSetName();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputPath)));
        treeConstructionInput.setBufferedReader(bufferedReader);
        treeConstructionInput.setFrameSize(miningInput.getFrameSize());
        treeConstructionInput.setWindowSize(miningInput.getWindowSize());
        treeConstructionInput.setSufCompleteCallback(new SufWindowCompletionCallback(miningInput));
        return treeConstructionInput;
    }
}
