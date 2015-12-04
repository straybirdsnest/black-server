package com.example;

import com.example.services.UserService;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.App.*;
import static com.example.config.security.TokenAuthenticationFilter.TOKEN_HEADER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserProfileTests extends BaseTests{
    public static final String UNREGISTERED_PHONE = "13728495536";

    @Autowired UserService userService;

    @Test
    public void register_withValidPhoneAndVcode() throws Exception {
        mockMvc.perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    public void unregister() throws Exception {
        MvcResult result = mockMvc
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        mockMvc.perform(delete(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProfile_username() throws Exception {
        MvcResult result = mockMvc
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
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
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
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
    public void updateProfile_avatar() throws Exception {
        MvcResult result = mockMvc
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        MockMultipartFile image = new MockMultipartFile("file", "my_avatar.png", "image/png",
                getClass().getResourceAsStream("/test_avatar.png"));

        MvcResult uploadResult = mockMvc.perform(fileUpload(API_IMAGE).file(image).header(TOKEN_HEADER, token))
                .andExpect(status().isCreated())
                .andReturn();

        String imageToken = uploadResult.getResponse().getContentAsString();

        mockMvc.perform(put(API_USER).header(TOKEN_HEADER, token)
                .param("avatar", imageToken))
                .andExpect(status().isOk());

        mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCurrentUserProfile() throws Exception {
        String token = createUserAndGetToken();

        mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.phone").value(UNREGISTERED_PHONE))
                .andExpect(jsonPath("$.username").value(UNREGISTERED_PHONE));
    }

    @Test
    public void getProfile() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_USER).header(TOKEN_HEADER, token))
                .andExpect(status().isOk()).andReturn();
        printFormatedJsonString(result);
    }

    private String createUserAndGetToken() throws Exception{
        MvcResult result = mockMvc
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject token = new JSONObject(result.getResponse().getContentAsString());
        return token.toString();
    }

    @Test
    public void getOthersProfile() throws Exception {
    }

    @Test
    public void updateToken() throws Exception {
        MvcResult result = mockMvc
                .perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(equalTo(token))));
    }

    @Test
    public void checkAvailability_phone() throws Exception {
        mockMvc.perform(get(API_TOKEN).param("phone", UNREGISTERED_PHONE).param("vcode", "1234"))
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(get(API_AVAILABILITY + "/phone").param("q", UNREGISTERED_PHONE))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
