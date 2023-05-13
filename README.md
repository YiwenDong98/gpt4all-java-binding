# gpt4all-java-binding

Binding for using gpt4all with Java.
Built using JPA.
Only linux `*.so` are included.
Other systems have not been tested.
Feel free to add them.

The underlying interface is very similar to the python interface.
Here's the python 3 colors example but in jshell.

```
[gpt4all-java-binding]$ jshell --class-path="gpt4all-java-binding-0.0.1-jar-with-dependencies.jar"
|  Welcome to JShell -- Version 11.0.19
|  For an introduction type: /help intro

jshell> import com.yiwendong.gpt4all.GPT4All;
jshell> import java.nio.file.Path;
jshell> import java.util.Arrays;
jshell> try (GPT4All gpt4All = new GPT4All(Path.of("ggml-gpt4all-l13b-snoozy.bin"), GPT4All.ModelType.LLAMA)) {
   ...>   GPT4All.Response response = gpt4All.chatCompletion(Arrays.asList(
   ...>     new GPT4All.Message(GPT4All.Message.Role.USER, "name 3 colors")
   ...>   ), true);
   ...> }
### Instruction: 
            The prompt below is a question to answer, a task to complete, or a conversation 
            to respond to; decide which and write an appropriate response.
            
### Prompt: 
name 3 colors
### Response:

Three colors are blue, green, and red.

jshell> 
```

Try it yourself
```java
import com.yiwendong.gpt4all.GPT4All;
import java.nio.file.Path;
import java.util.Arrays;
try (GPT4All gpt4All = new GPT4All(Path.of("ggml-gpt4all-l13b-snoozy.bin"), GPT4All.ModelType.LLAMA)) {
  GPT4All.Response response = gpt4All.chatCompletion(Arrays.asList(
    new GPT4All.Message(GPT4All.Message.Role.USER, "name 3 colors")
  ), true);
}
```

### Example Application

Sample TerminalChatMain application is available. 
Download the following jar and model and run this command.

`java -jar gpt4all-java-binding-0.0.1-jar-with-dependencies.jar ggml-gpt4all-l13b-snoozy.bin llama`

Example output:

```
[gpt4all-java-binding]$ java -jar gpt4all-java-binding-0.0.1-jar-with-dependencies.jar ggml-gpt4all-l13b-snoozy.bin llama
Loading...
llama.cpp: loading model from ../test-models/ggml-gpt4all-l13b-snoozy.bin
llama_model_load_internal: format     = ggjt v1 (latest)
llama_model_load_internal: n_vocab    = 32000
llama_model_load_internal: n_ctx      = 2048
llama_model_load_internal: n_embd     = 5120
llama_model_load_internal: n_mult     = 256
llama_model_load_internal: n_head     = 40
llama_model_load_internal: n_layer    = 40
llama_model_load_internal: n_rot      = 128
llama_model_load_internal: ftype      = 2 (mostly Q4_0)
llama_model_load_internal: n_ff       = 13824
llama_model_load_internal: n_parts    = 1
llama_model_load_internal: model size = 13B
llama_model_load_internal: ggml ctx size =  73.73 KB
llama_model_load_internal: mem required  = 9807.47 MB (+ 1608.00 MB per state)
llama_init_from_file: kv self size  = 1600.00 MB

Prompt[0]: hello
Thinking...
Response: 
Hello! How can I assist you today?

Prompt[1]: Name 3 colors
Thinking...
Response:  
Red, blue, and green.

Prompt[2]: exit
```