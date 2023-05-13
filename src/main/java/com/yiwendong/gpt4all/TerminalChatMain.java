package com.yiwendong.gpt4all;

import com.yiwendong.gpt4all.GPT4All.Message;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalChatMain {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Please provide the model path as the first argument and model type [gptj/llama] as the second argument");
            return;
        }
        Path modelPath = Path.of(args[0]);
        if (!modelPath.toFile().exists()) {
            System.out.println("Model at " + modelPath.toAbsolutePath() + " cannot be found.");
            return;
        }
        GPT4All.ModelType modelType = null;
        if (args[1].equalsIgnoreCase("gptj")) {
            modelType = GPT4All.ModelType.GPTJ;
        }
        if (args[1].equalsIgnoreCase("llama")) {
            modelType = GPT4All.ModelType.LLAMA;
        }
        if (modelType == null) {
            System.out.println("Model type " + args[1] + " is not recognized.");
            return;
        }

        List<Message> messages = new ArrayList<>();
        System.out.println("Loading...");
        System.out.flush();
        try (Scanner scanner = new Scanner(System.in);
             GPT4All gpt4All = new GPT4All(modelPath, modelType)) {
            System.out.println();
            System.out.print("Prompt[0]: ");
            System.out.flush();

            while (scanner.hasNextLine()) {
                String prompt = scanner.nextLine();

                if (prompt.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Thinking...");
                System.out.flush();
                Message promptMessage = new Message(Message.Role.USER, prompt);
                messages.add(promptMessage);
                GPT4All.Response response = gpt4All.chatCompletion(messages);
                System.out.println("Response: " + response.choices.get(0).content);
                messages.add(response.choices.get(0));
                System.out.println();
                System.out.print("Prompt[" + messages.size() / 2 + "]: ");
                System.out.flush();
            }
        }
    }
}
