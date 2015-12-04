package com.example;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.App.API_ACTIVITY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityTests extends BaseTests {

    @Test
    public void getRecommendedActivities() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_ACTIVITY).header("X-Token", token)
                .param("category", "recommendations")
                .param("page", "0")
                .param("size", "5")
        )
                .andExpect(status().isOk())
                .andReturn();
        printFormatedJsonString(result);
    }

    @Test
    public void getSameSchoolActivities() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_ACTIVITY).header("X-Token", token)
                .param("category", "school")
                .param("page", "0")
                .param("size", "5")
        )
                .andExpect(status().isOk())
                .andReturn();
        printFormatedJsonString(result);
    }


}
