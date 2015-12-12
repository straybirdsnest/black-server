package org.team10424102.blackserver;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.text.IsEmptyString.*;

import static org.team10424102.blackserver.App.API_USER;

public class ApiTests extends BaseTests {

    @Test
    public void getToken_withInvalidPhone() throws Exception {
        //TODO 使用真正的手机短信验证框架后, 修改该测试
        mockMvc.perform(get(API_USER + "/token")
                .param("phone", "11111111111")
                .param("vcode", "1234"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getToken_withInvalidVcode() throws Exception {
        mockMvc.perform(get(API_USER + "/token")
                .param("phone", "15610589653")
                .param("vcode", "xxxx"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getToken_withValidVcode() throws Exception {
        //TODO 使用真正的手机短信验证框架后, 修改该测试
        mockMvc.perform(get(API_USER + "/token")
                .param("phone", "15610589653")
                .param("vcode", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", matchesPattern(UUID_PATTERN)));
    }

    @Test
    public void getToken_missingParameter() throws Exception {
        mockMvc.perform(get(API_USER + "/token")
                .param("phone", "15610589653"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getMyProfile() throws Exception {
        MvcResult result = mockMvc.perform(get(API_USER).header(AUTH_HEADER, getToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(2)))
                .andExpect(jsonPath("$.username", equalTo("TEST")))
                .andExpect(jsonPath("$.nickname", equalTo("测试专用用户")))
                .andExpect(jsonPath("$.email", equalTo("test@example.com")))
                .andExpect(jsonPath("$.gender", equalTo("OTHER")))
                .andExpect(jsonPath("$.birthday", equalTo("2015-12-12")))
                .andExpect(jsonPath("$.phone", equalTo("18921273088")))
                .andExpect(jsonPath("$.signature", anything()))
                .andExpect(jsonPath("$.hometown", anything()))
                .andExpect(jsonPath("$.highschool", anything()))
                .andExpect(jsonPath("$.grade", anything()))
                .andExpect(jsonPath("$.hometown", anything()))
                .andExpect(jsonPath("$.avatar", matchesPattern(UUID_PATTERN)))
                .andExpect(jsonPath("$.background", matchesPattern(UUID_PATTERN)))
                .andExpect(jsonPath("$.college", anything()))
                .andExpect(jsonPath("$.academy", anything()))
                .andExpect(jsonPath("$.friends", isA(Integer.class)))
                .andExpect(jsonPath("$.fans", isA(Integer.class)))
                .andExpect(jsonPath("$.focuses", isA(Integer.class)))
                .andReturn();
        printFormatedJsonString(result);
    }

    

}
