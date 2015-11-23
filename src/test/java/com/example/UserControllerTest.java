package com.example;

import com.example.models.User;
import com.example.services.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired WebApplicationContext webApplicationContext;
    @Autowired TokenService tokenService;
    private MockMvc mockMvc;
    private String xtoken;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // SetUp fake User
        User user = new User();
        user.setId(1);
        xtoken = tokenService.generateToken(user);
    }

    @Test
    public void getMyProfile() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/api/profile").header("X-Token", xtoken).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.realName").value("王尼玛"))
                .andExpect(jsonPath("$.idCard").value("123456789"))
                .andExpect(jsonPath("$.signature").value("我是王尼玛，萌萌的"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.hometown").value("上海"))
                .andExpect(jsonPath("$.highschool").value("暴走高中"))
                .andExpect(jsonPath("$.username").value("王尼玛"))
                .andExpect(jsonPath("$.college").value("上海大学"))
                .andExpect(jsonPath("$.academy").value("计算机工程与科学学院"))
                .andExpect(jsonPath("$.grade").value("研究生一年级"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andDo(MockMvcResultHandlers.print());
        // @formatter:on
    }

    @Test
    public void getUserProfile() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/api/users/1/profile").header("X-Token", xtoken).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.realName").value("王尼玛"))
                .andExpect(jsonPath("$.idCard").value("123456789"))
                .andExpect(jsonPath("$.signature").value("我是王尼玛，萌萌的"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.hometown").value("上海"))
                .andExpect(jsonPath("$.highschool").value("暴走高中"))
                .andExpect(jsonPath("$.username").value("王尼玛"))
                .andExpect(jsonPath("$.college").value("上海大学"))
                .andExpect(jsonPath("$.academy").value("计算机工程与科学学院"))
                .andExpect(jsonPath("$.grade").value("研究生一年级"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andDo(MockMvcResultHandlers.print());
        // @formatter:on
    }

}
