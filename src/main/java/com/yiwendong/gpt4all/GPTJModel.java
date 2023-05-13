package com.yiwendong.gpt4all;

public class GPTJModel extends LLModel {

    public GPTJModel() {
        super(LLModel.getInstance().llmodel_gptj_create(), "gptj");
    }

    @Override
    public void close() throws Exception {
        LLModel.getInstance().llmodel_gptj_destroy(super.model);
    }
}
