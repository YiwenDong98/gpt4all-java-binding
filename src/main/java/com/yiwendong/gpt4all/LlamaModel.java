package com.yiwendong.gpt4all;

public class LlamaModel extends LLModel {

    public LlamaModel() {
        super(LLModel.getInstance().llmodel_llama_create(), "llama");
    }

    @Override
    public void close() throws Exception {
        LLModel.getInstance().llmodel_llama_destroy(super.model);
    }
}
