package org.team10424102;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.team10424102.App.API_ACTIVITY;

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

    @Test
    public void getActivityDetails() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_ACTIVITY + "/1").header("X-Token", token))
                .andExpect(status().isOk())
                .andReturn();
        printFormatedJsonString(result);
    }


}
