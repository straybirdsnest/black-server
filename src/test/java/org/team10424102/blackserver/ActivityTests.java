package org.team10424102.blackserver;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityTests extends BaseTests {

//    @Test
//    public void getRecommendedActivities() throws Exception {
//        String token = getToken();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(App.API_ACTIVITY).header("X-Token", token)
//                .param("category", "recommendations")
//                .param("page", "0")
//                .param("size", "5")
//        )
//                .andExpect(status().isOk())
//                .andReturn();
//        printFormatedJsonString(result);
//    }
//
//    @Test
//    public void getSameSchoolActivities() throws Exception {
//        String token = getToken();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(App.API_ACTIVITY).header("X-Token", token)
//                .param("category", "school")
//                .param("page", "0")
//                .param("size", "5")
//        )
//                .andExpect(status().isOk())
//                .andReturn();
//        printFormatedJsonString(result);
//    }
//
//    @Test
//    public void getActivityDetails() throws Exception {
//        String token = getToken();
//        MvcResult result = mockMvc.perform(get(App.API_ACTIVITY + "/1").header("X-Token", token))
//                .andExpect(status().isOk())
//                .andReturn();
//        printFormatedJsonString(result);
//    }
//
//    @Test
//    @Rollback(false)
//    public void addLike() throws Exception {
//        String token = getToken();
//        mockMvc.perform(post(App.API_ACTIVITY + "/2/like/add")
//            .header("X-Token", token)
//            .header("Accept-Language", "zh_CN"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @Rollback(false)
//    public void deleteLike() throws Exception {
//        String token = getToken();
//        mockMvc.perform(post(App.API_ACTIVITY + "/2/like/delete")
//            .header("X-Token", token)
//            .header("Accept-Language", "zh_CN"))
//            .andExpect(status().isOk());
//    }
//
//
//    @Test
//    public void getComments() throws Exception {
//        String token = getToken();
//
//        MvcResult result = mockMvc.perform(get(App.API_ACTIVITY + "/2/comment")
//            .header("X-Token", token)
//            .header("Accept-Language", "zh_CN"))
//            .andExpect(status().isOk())
//            .andReturn();
//        printFormatedJsonString(result);
//    }
//
//    @Test
//    @Rollback(false)
//    public void addComment() throws Exception {
//        String token = getToken();
//        mockMvc.perform(post(App.API_ACTIVITY + "/2/comment/add")
//            .header("X-Token", token)
//            .header("Accept-Language", "zh_CN")
//            .param("content", "梅杰菜狗"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @Rollback(false)
//    public void deleteComment() throws Exception {
//        String token = getToken();
//        mockMvc.perform(delete(App.API_ACTIVITY + "/2/comment/delete/29")
//            .header("X-Token", token)
//            .header("Accept-Language", "zh_CN"))
//            .andExpect(status().isOk());
//    }
}
