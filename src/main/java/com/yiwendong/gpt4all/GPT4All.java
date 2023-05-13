package com.yiwendong.gpt4all;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class GPT4All {

    private LLModel llModel;
    public GPT4All(Path modelPath) {
    }

    public String generate(String prompt) {
        throw new UnsupportedOperationException("");
    }

    public Map<String, String> chatCompletion(List<Map<String, String>> messages) {
        throw new UnsupportedOperationException("");
    }
}
