package com.example;

import com.example.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * Created by yy on 8/30/15.
 */
public class JacksonTest {
    @Test
    public void write() throws Exception{
        User u = new User("test", "123");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(u));
    }
}
