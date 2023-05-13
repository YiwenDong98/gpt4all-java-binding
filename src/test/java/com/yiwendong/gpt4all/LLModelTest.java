package com.yiwendong.gpt4all;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LLModelTest {

    LLModel getTestModel() {
        return new LLModel(LLModel.getInstance().llmodel_llama_create(), "llama") {
            @Override
            public void close() throws Exception {
                llModel.llmodel_llama_destroy(model);
            }
        };
    }

    @Test
    void getInstance() {
        assertNotNull(LLModel.getInstance());
        assertNotNull(LLModel.getInstance().llmodel_gptj_create());
        assertNotEquals(Pointer.NULL, LLModel.getInstance().llmodel_gptj_create());
    }

    @Test
    void llmodelConstructor() throws Exception {
        try (LLModel llamaModel = getTestModel()) {
            assertEquals("llama", llamaModel.modelType);
            assertNotNull(llamaModel.model);
            assertNotEquals(Pointer.NULL, llamaModel.model);
        }
    }

    @Test
    void loadModel() throws Exception {
        try (LLModel llamaModel = getTestModel()) {
            assertTrue(llamaModel.loadModel(Path.of("test-models/ggml-gpt4all-l13b-snoozy.bin")));
        }
    }

    @Test
    void generate() throws Exception {
        try (LLModel llamaModel = getTestModel()) {
            llamaModel.loadModel(Path.of("test-models/ggml-gpt4all-l13b-snoozy.bin"));
            String modelOutput = llamaModel.generate("hello", LLModel.GenerationConfig.DEFAULT_CONFIG);
            assertNotEquals("", modelOutput);
            System.out.println(modelOutput);
        }
    }
}