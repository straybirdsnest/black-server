package com.example;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.App.API_POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTests extends BaseTests {

    @Test
    public void createPost() throws Exception{
        String token = getToken();

        mockMvc.perform(post(API_POST).param("content", "不要给我生猴子").header("X-Token", token))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePost() {

    }

    @Test
    public void getSchoolPosts() throws Exception {
        String token = getToken();

        MvcResult result = mockMvc.perform(get(API_POST + "/SCHOOL?page=0&size=5").header("X-Token", token))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andReturn();

        printFormatedJsonString(result);

    }

}
