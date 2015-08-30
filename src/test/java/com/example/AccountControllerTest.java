package com.example;

import com.example.daos.UserRepository;
import com.example.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class AccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    UserRepository repository;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        // 存两个用户试一试
        repository.save(new User("test1", "123"));
        repository.save(new User("test2", "123"));
    }


    @Test
    public void registration() throws Exception {
        mockMvc.perform(post("/register").param("username", "user").param("password", "password"))
                .andExpect(status().isOk());
    }

    @Test
    public void getToken() throws Exception {
        mockMvc.perform(get("/api/token").param("username", "test1").param("password", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("这就是一个加密了的 Token"));
    }

}
