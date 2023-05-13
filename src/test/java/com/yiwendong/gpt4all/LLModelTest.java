package com.yiwendong.gpt4all;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LLModelTest {

    @Test
    void getInstance() {
        assertNotNull(LLModel.getInstance());
        assertNotNull(LLModel.getInstance().llmodel_gptj_create());
        assertNotEquals(Pointer.NULL, LLModel.getInstance().llmodel_gptj_create());
    }

    @Test
    void loadModel() {
        LLModel llamaModel = new LLModel(LLModel.getInstance().llmodel_llama_create(), "llama") {
            @Override
            public void close() throws Exception {
                llModel.llmodel_llama_destroy(model);
            }
        };
        try (llamaModel) {
            assertEquals("llama", llamaModel.modelType);
            assertNotNull(llamaModel.model);
            assertNotEquals(Pointer.NULL, llamaModel.model);
            assertTrue(llamaModel.loadModel(Path.of("test-models/ggml-gpt4all-l13b-snoozy.bin")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generate() {
    }
}