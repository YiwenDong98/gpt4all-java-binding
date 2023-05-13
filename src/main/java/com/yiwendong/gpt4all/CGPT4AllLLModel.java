package com.yiwendong.gpt4all;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.unix.LibCAPI.size_t;

public interface CGPT4AllLLModel extends Library {
    @Structure.FieldOrder({"logits"})
    public static class LLModelPromptContext extends Structure {
        public static class ByValue extends LLModelPromptContext implements Structure.ByValue {}

        Pointer logits;          // logits of current context
        size_t logits_size;      // the size of the raw logits vector
        Pointer tokens;          // current tokens in the context window
        size_t tokens_size;      // the size of the raw tokens vector
        int n_past;              // number of tokens in past conversation
        int n_ctx;               // number of tokens possible in context window
        int n_predict;           // number of tokens to predict
        int top_k;               // top k logits to sample from
        float top_p;             // nucleus sampling probability threshold
        float temp;              // temperature to adjust model's output distribution
        int n_batch;             // number of predictions to generate in parallel
        float repeat_penalty;    // penalty factor for repeated tokens
        int repeat_last_n;       // last n tokens to penalize
        float context_erase;     // percent of context to erase if we exceed the context window
    }

    public interface LLModelResponseCallback extends Callback {
        boolean callback(int tokenId, String response);
    }

    public interface LLModelRecalculateCallback extends Callback {
        boolean callback(boolean isRecalculating);
    }

    Pointer llmodel_gptj_create();
    void llmodel_gptj_destroy(Pointer gptj);
    Pointer llmodel_llama_create();
    void llmodel_llama_destroy(Pointer llama);
    boolean llmodel_loadModel(Pointer model, String modelPath);
    boolean llmodel_isModelLoaded(Pointer model);
    void llmodel_prompt(Pointer model, String prompt,
                        LLModelResponseCallback promptCallback,
                        LLModelResponseCallback responseCallback,
                        LLModelRecalculateCallback recalculateCallback,
                        LLModelPromptContext context);
}
