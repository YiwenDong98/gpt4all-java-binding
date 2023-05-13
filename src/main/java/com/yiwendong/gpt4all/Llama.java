package com.yiwendong.gpt4all;

import com.sun.jna.Native;

public class Llama {
    private static CGPT4AllLlama INSTANCE = null;

    public static CGPT4AllLlama getInstance() {
        if (INSTANCE == null) {
            INSTANCE = Native.load("llama", CGPT4AllLlama.class);
        }
        return INSTANCE;
    }
}
