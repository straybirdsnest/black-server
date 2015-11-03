package com.example.experimental;

import org.junit.Test;

public class CamelSpliter {
    @Test
    public void test1() {
        String s = "MyEntityClass";
        String regex = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        for (String w : s.split(regex)) {
            System.out.println(w);
        }
    }
}
