package com.example;

import com.example.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.transaction.Transactional;

import static com.example.App.API_USER;
import static com.example.config.security.TokenAuthenticationFilter.TOKEN_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") // random port
@Transactional
public class UserControllerTest {
    public static final String UNREGISTERED_PHONE = "13728495536";
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired WebApplicationContext webApplicationContext;
    @Autowired UserService userService;
    private MockMvc mockMvc;
    private String xtoken;

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain).build();
//        // SetUp fake User
//        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
//        User user = userService.createAndSaveUser("13728495536", mockedRequest);
//        xtoken = tokenService.generateToken(user);
    }

    @Test
    public void register_withValidPhoneAndVcode() throws Exception {
        mockMvc.perform(post(API_USER).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    public void unregister() throws Exception {
        MvcResult result = mockMvc
                .perform(post(API_USER).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        mockMvc.perform(delete(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProfile_username() throws Exception {
        MvcResult result = mockMvc
                .perform(post(API_USER).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put(API_USER)
                .header(TOKEN_HEADER, token)
                .param("username", "张三"))
                .andExpect(status().isOk());

        mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(UNREGISTERED_PHONE))
                .andExpect(jsonPath("$.username").value("张三"));
    }

    @Test
    public void updateProfile_signatureAndUsername() throws Exception {
        MvcResult result = mockMvc
                .perform(post(API_USER).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put(API_USER)
                .header(TOKEN_HEADER, token)
                .param("signature", "今天好开心")
                .param("username", "张三"))
                .andExpect(status().isOk());

        mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.phone").value(UNREGISTERED_PHONE))
                .andExpect(jsonPath("$.username").value("张三"))
                .andExpect(jsonPath("$.signature").value("今天好开心"));
    }

    @Test
    public void getCurrentUserProfile() throws Exception {
        MvcResult result = mockMvc
                .perform(post(API_USER).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(UNREGISTERED_PHONE))
                .andExpect(jsonPath("$.username").value(UNREGISTERED_PHONE));
    }

    @Test
    public void getOthersProfile() {

    }

    @Test
    public void updateToken() {

    }

    @Test
    public void checkAvailability() {

    }

    @Test
    public void getFocuses() {

    }

    @Test
    public void getFans() {

    }

    @Test
    public void focusSomeone() {

    }

    @Test
    public void unfocusSomone() {

    }

    @Test
    public void getFriends() {

    }

    @Test
    public void friendSomeone() {

    }

    @Test
    public void unfriendSomeone() {

    }

    @Test
    public void updateFriendAlias() {

    }


    @Test
    @Transactional
    @Rollback
    public void getProfile() throws Exception {
    }

//    @Test
//    public void getMyProfile() throws Exception {
//        // @formatter:off
//        mockMvc.perform(get("/api/profile").header("X-Token", xtoken).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.phone").value("123456789"))
//                .andExpect(jsonPath("$.realName").value("王尼玛"))
//                .andExpect(jsonPath("$.idCard").value("123456789"))
//                .andExpect(jsonPath("$.signature").value("我是王尼玛，萌萌的"))
//                .andExpect(jsonPath("$.gender").value("MALE"))
//                .andExpect(jsonPath("$.hometown").value("上海"))
//                .andExpect(jsonPath("$.highschool").value("暴走高中"))
//                .andExpect(jsonPath("$.username").value("王尼玛"))
//                .andExpect(jsonPath("$.college").value("上海大学"))
//                .andExpect(jsonPath("$.academy").value("计算机工程与科学学院"))
//                .andExpect(jsonPath("$.grade").value("研究生一年级"))
//                .andExpect(jsonPath("$.email").value("test@test.com"))
//                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
//                .andDo(MockMvcResultHandlers.print());
//        // @formatter:on
//    }
//
//    @Test
//    public void getUserProfile() throws Exception {
//        // @formatter:off
//        mockMvc.perform(get("/api/users/1/profile").header("X-Token", xtoken).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.phone").value("123456789"))
//                .andExpect(jsonPath("$.realName").value("王尼玛"))
//                .andExpect(jsonPath("$.idCard").value("123456789"))
//                .andExpect(jsonPath("$.signature").value("我是王尼玛，萌萌的"))
//                .andExpect(jsonPath("$.gender").value("MALE"))
//                .andExpect(jsonPath("$.hometown").value("上海"))
//                .andExpect(jsonPath("$.highschool").value("暴走高中"))
//                .andExpect(jsonPath("$.username").value("王尼玛"))
//                .andExpect(jsonPath("$.college").value("上海大学"))
//                .andExpect(jsonPath("$.academy").value("计算机工程与科学学院"))
//                .andExpect(jsonPath("$.grade").value("研究生一年级"))
//                .andExpect(jsonPath("$.email").value("test@test.com"))
//                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
//                .andDo(MockMvcResultHandlers.print());
//        // @formatter:on
//    }

}
