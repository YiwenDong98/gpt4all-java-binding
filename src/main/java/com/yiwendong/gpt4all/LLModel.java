package com.yiwendong.gpt4all;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.LibCAPI;

import java.nio.file.Path;

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
    protected final CGPT4AllLLModel cllModel;

    protected LLModel(Pointer model, String modelType) {
        this.model = model;
        this.modelType = modelType;
        this.cllModel = LLModel.getInstance();
    }

    public boolean loadModel(Path modelPath) {
        cllModel.llmodel_loadModel(model, modelPath.toString());
        String fileName = modelPath.toFile().getName();
        this.modelName = fileName.split("\\.(?=[^\\.]+$)")[0];

        if (cllModel.llmodel_isModelLoaded(model)) {
            return true;
        } else {
            return false;
        }

    }

    public String generate(String prompt, GenerationConfig generationConfig) {
        CGPT4AllLLModel.LLModelPromptContext context = new CGPT4AllLLModel.LLModelPromptContext();
        context.logits_size = new LibCAPI.size_t(generationConfig.logitsSize);
        context.tokens_size = new LibCAPI.size_t(generationConfig.tokensSize);
        context.n_past = generationConfig.nPast;
        context.n_ctx = generationConfig.nCtx;
        context.n_predict = generationConfig.nPredict;
        context.top_k = generationConfig.topK;
        context.top_p = generationConfig.topP;
        context.temp = generationConfig.temp;
        context.n_batch = generationConfig.nBatch;
        context.repeat_penalty = generationConfig.repeatPenalty;
        context.repeat_last_n = generationConfig.repeatLastN;
        context.context_erase = generationConfig.contextErase;

        StringBuffer responseBuffer = new StringBuffer();

        cllModel.llmodel_prompt(this.model,
                prompt,
                EMPTY_PROMPT_CALLBACK,
                collectResponseCallBackToString(responseBuffer),
                EMPTY_RECALCULATE_CALLBACK,
                context);

        return responseBuffer.toString();
    }

    public static final CGPT4AllLLModel.LLModelResponseCallback EMPTY_PROMPT_CALLBACK =
            (tokenId, response) -> true;
    public static final CGPT4AllLLModel.LLModelResponseCallback PRINT_RESPONSE_CALLBACK =
            (tokenId, response) -> {
                System.out.println(response);
                return true;
            };
    public static final CGPT4AllLLModel.LLModelRecalculateCallback EMPTY_RECALCULATE_CALLBACK =
            (isRecalculating) -> true;

    public static CGPT4AllLLModel.LLModelResponseCallback collectResponseCallBackToString(StringBuffer stringBuffer) {
        return (tokenId, response) -> {
            stringBuffer.append(response);
            return true;
        };
    }

    public static class GenerationConfig {
        public final int logitsSize;
        public final int tokensSize;
        public final int nPast;
        public final int nCtx;
        public final int nPredict;
        public final int topK;
        public final float topP;
        public final float temp;
        public final int nBatch;
        public final float repeatPenalty;
        public final int repeatLastN;
        public final float contextErase;

        public GenerationConfig(int logitsSize, int tokensSize, int nPast, int nCtx, int nPredict, int topK, float topP, float temp, int nBatch, float repeatPenalty, int repeatLastN, float contextErase) {
            this.logitsSize = logitsSize;
            this.tokensSize = tokensSize;
            this.nPast = nPast;
            this.nCtx = nCtx;
            this.nPredict = nPredict;
            this.topK = topK;
            this.topP = topP;
            this.temp = temp;
            this.nBatch = nBatch;
            this.repeatPenalty = repeatPenalty;
            this.repeatLastN = repeatLastN;
            this.contextErase = contextErase;
        }

        private GenerationConfig() {
            this(0, 0, 0, 1024, 128, 40, .9f, .1f, 8, 1.2f, 10, .5f);
        }
        public static final GenerationConfig DEFAULT_CONFIG = new GenerationConfig();


    }

}
