package com.yiwendong.gpt4all;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class LLModel implements AutoCloseable {

    private static CGPT4AllLLModel INSTANCE = null;

    protected static CGPT4AllLLModel getInstance() {
        CGPT4AllLlama CGPT4AllLlama = Llama.getInstance();
        if (INSTANCE == null) {
            INSTANCE = Native.load("llmodel", CGPT4AllLLModel.class);
        }
        return INSTANCE;
    }

    protected final Pointer model;
    protected final String modelType;
    protected String modelName;
    protected final CGPT4AllLLModel llModel;

    protected LLModel(Pointer model, String modelType) {
        this.model = model;
        this.modelType = modelType;
        this.llModel = LLModel.getInstance();
    }

    public boolean loadModel(Path modelPath) {
        llModel.llmodel_loadModel(model, modelPath.toString());
        String fileName = modelPath.toFile().getName();
        this.modelName = fileName.split("\\.(?=[^\\.]+$)")[0];

        if (llModel.llmodel_isModelLoaded(model)) {
            return true;
        } else {
            return false;
        }

    }

    public String generate(String prompt) {
        throw new UnsupportedOperationException("");
    }

    public static final CGPT4AllLLModel.LLModelResponseCallback EMPTY_PROMPT_CALLBACK =
            (tokenId, response) -> true;
    public static final CGPT4AllLLModel.LLModelResponseCallback EMPTY_RESPONSE_CALLBACK =
            (tokenId, response) -> true;
    public static final CGPT4AllLLModel.LLModelRecalculateCallback EMPTY_RECALCULATE_CALLBACK =
            (isRecalculating) -> true;

}
