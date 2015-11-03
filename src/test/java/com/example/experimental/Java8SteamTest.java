package com.example.experimental;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Java8SteamTest {
    @Test
    public void test1() {
        String[] a = {"aaa", "bbb", "ccc"};
        String result = Arrays.stream(a).map(String::toUpperCase)
                .collect(Collectors.joining("_"));
        System.out.println(result);
    }
}
