package com.yiwendong.gpt4all;

import com.sun.jna.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {

    public static String getResourceAsString(String resourcePath) throws IOException {
        try (InputStream in = getResource(resourcePath)) {
            return new BufferedReader(
                    new InputStreamReader(in))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
    }

    public static InputStream getResource(String resourcePath) throws IOException {
        InputStream in = Utils.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IOException(resourcePath + " not found");
        }
        return in;
    }
}
