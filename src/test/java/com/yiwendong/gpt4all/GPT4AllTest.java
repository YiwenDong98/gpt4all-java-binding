package com.yiwendong.gpt4all;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GPT4AllTest {

    GPT4All getGPT4All() {
        return new GPT4All(Path.of("test-models/ggml-gpt4all-l13b-snoozy.bin"), GPT4All.ModelType.LLAMA);
    }

    @Test
    void generate() throws Exception {
        try (GPT4All gpt4All = getGPT4All()) {
            assertNotEquals("", gpt4All.generate("hello"));
        }
    }

    @Test
    void chatCompletion() throws Exception {
        try (GPT4All gpt4All = getGPT4All()) {
            GPT4All.Response response = gpt4All.chatCompletion(Arrays.asList(
                    new GPT4All.Message(GPT4All.Message.Role.USER, "hello")
            ), true);
            assertNotEquals(0, response.choices.size());
            assertEquals(GPT4All.Message.Role.ASSISTANT, response.choices.get(0).role);
            assertNotEquals("", response.choices.get(0).content);
        }
    }

    @Test
    void chatCompletion3Colors() throws Exception {
        try (GPT4All gpt4All = getGPT4All()) {
            GPT4All.Response response = gpt4All.chatCompletion(Arrays.asList(
                    new GPT4All.Message(GPT4All.Message.Role.USER, "name 3 colors")
            ), true);
            assertNotEquals(0, response.choices.size());
            assertEquals(GPT4All.Message.Role.ASSISTANT, response.choices.get(0).role);
            assertNotEquals("", response.choices.get(0).content);
        }
    }
}