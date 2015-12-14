package org.team10424102.blackserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.team10424102.blackserver.models.Notification;

import java.util.List;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.team10424102.blackserver.App.API_NOTIFICATION;
import static org.team10424102.blackserver.App.API_USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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
                .andExpect(jsonPath("$.id", equalTo(2)))
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

    @Test
    public void addFriend_replyYes() throws Exception {
        mockMvc.perform(post(API_USER + "/friends/" + USER_B_ID)
                .param("attachment", "我是隔壁老王啊")
                .header(AUTH_HEADER, getTokenA()))
                .andExpect(status().isOk());


        MvcResult result = mockMvc.perform(get(API_NOTIFICATION).header(AUTH_HEADER, getTokenA()))
                .andExpect(status().isOk())
                .andReturn();

        printFormatedJsonString(result);

        MvcResult result2 = mockMvc.perform(get(API_NOTIFICATION).header(AUTH_HEADER, getTokenB()))
                .andExpect(status().isOk())
                .andReturn();

        printFormatedJsonString(result2);

        System.out.println("----------------");

        JSONArray notificationArray = new JSONArray(result2.getResponse().getContentAsString());
        JSONObject notification = (JSONObject)notificationArray.get(0);
        long id = notification.getInt("id");

        mockMvc.perform(put(API_NOTIFICATION + "/" + id)
                .param("reply", Notification.REPLY_YES + "")
                .header(AUTH_HEADER, getTokenA()))
                .andExpect(status().isOk())
                .andReturn();


        MvcResult result3 = mockMvc.perform(get(API_NOTIFICATION).header(AUTH_HEADER, getTokenA()))
                .andExpect(status().isOk())
                .andReturn();

        printFormatedJsonString(result3);

        MvcResult result4 = mockMvc.perform(get(API_NOTIFICATION).header(AUTH_HEADER, getTokenB()))
                .andExpect(status().isOk())
                .andReturn();

        printFormatedJsonString(result4);

    }

    @Test
    public void addFriend_replyNo() throws Exception {

    }

    @Test
    public void addFriend_replyDelete() throws Exception {

    }

    @Test
    public void removeFriend() throws Exception {

    }


}
