package com.yiwendong.gpt4all;

import com.yiwendong.gpt4all.LLModel.GenerationConfig;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class GPT4All implements AutoCloseable {

    private LLModel llModel;
    public GPT4All(Path modelPath, ModelType modelType) {
        switch (modelType) {
            case GPTJ:
                this.llModel = new GPTJModel();
                break;
            case LLAMA:
                this.llModel = new LlamaModel();
                break;
        }
        if (!modelPath.toFile().exists()) {
            throw new RuntimeException("Model at " + modelPath.toAbsolutePath() + " not found.");
        }
        this.llModel.loadModel(modelPath);
    }
    public GPT4All(String modelName) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void close() throws Exception {
        llModel.close();
    }

    public String generate(String prompt, GenerationConfig generationConfig) {
        return this.llModel.generate(prompt, generationConfig);
    }

    public String generate(String prompt) {
        return generate(prompt, GenerationConfig.DEFAULT_CONFIG);
    }

    public Response chatCompletion(List<Message> messages, boolean defaultPromptHeader, boolean defaultPromptFooter, boolean verbose) {
        String prompt = buildPrompt(messages, defaultPromptHeader, defaultPromptFooter);

        if (verbose) {
            System.out.println(prompt);
        }
        String response = generate(prompt);
        if (verbose) {
            System.out.println(response);
        }

        return new Response(
                this.llModel.modelName,
                prompt.length(),
                response.length(),
                prompt.length() + response.length(),
                List.of(new Message(Message.Role.ASSISTANT, response)));
    }

    public Response chatCompletion(List<Message> messages, boolean verbose) {
        return chatCompletion(messages, true, true, verbose);
    }

    public Response chatCompletion(List<Message> messages) {
        return chatCompletion(messages, true, true, false);
    }

    private static String buildPrompt(List<Message> messages, boolean defaultPromptHeader, boolean defaultPromptFooter) {
        StringBuilder promptBuilder = new StringBuilder();

        for (Message message : messages) {
            if (Message.Role.SYSTEM.equals(message.role)) {
                promptBuilder.append(message.content);
            }
        }
        if (defaultPromptHeader) {
            promptBuilder.append(DEFAULT_PROMPT_HEADER);
        }
        for (Message message : messages) {
            switch (message.role) {
                case SYSTEM:
                    break;
                case USER:
                    promptBuilder.append("\n").append(message.content);
                    break;
                case ASSISTANT:
                    promptBuilder.append("\n### Response: ").append(message.content);
                    break;
            }
        }
        if (defaultPromptFooter) {
            promptBuilder.append(DEFAULT_PROMPT_FOOTER);
        }
        return promptBuilder.toString();
    }

    private static final String DEFAULT_PROMPT_HEADER = "### Instruction: \n" +
            "            The prompt below is a question to answer, a task to complete, or a conversation \n" +
            "            to respond to; decide which and write an appropriate response.\n" +
            "            \n### Prompt: ";
    private static final String DEFAULT_PROMPT_FOOTER = "\n### Response:";

    public enum ModelType {
        GPTJ,
        LLAMA
    }

    public static class Message {

        public final Role role;
        public final String content;

        public Message(Role role, String content) {
            this.role = role;
            this.content = content;
        }

        public enum Role {
            SYSTEM,
            USER,
            ASSISTANT
        }
    }

    public static class Response {
        public final String modelName;
        public final int promptTokens;
        public final int completionTokens;
        public final int totalTokens;
        public final List<Message> choices;

        public Response(String modelName, int promptTokens, int completionTokens, int totalTokens, List<Message> choices) {
            this.modelName = modelName;
            this.promptTokens = promptTokens;
            this.completionTokens = completionTokens;
            this.totalTokens = totalTokens;
            this.choices = Collections.unmodifiableList(choices);
        }
    }
}
