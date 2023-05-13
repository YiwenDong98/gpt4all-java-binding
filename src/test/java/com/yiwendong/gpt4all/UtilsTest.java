package com.yiwendong.gpt4all;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void testGetResourceAsString() throws IOException {
        Assertions.assertNotEquals("", Utils.getResourceAsString("/headers/common.h"));
    }

}
