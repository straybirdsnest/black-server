package com.example;

import org.junit.Test;

import static com.example.App.API_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRelationTests extends BaseTests{

    @Test
    public void focusSomeone() throws Exception {
        mockMvc.perform(post(API_USER).param("phone", "1234").param("vcode", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    public void friendSomeone() {

    }

    @Test
    public void getFocuses() {

    }

    @Test
    public void getFans() {

    }

    @Test
    public void unfocusSomone() {

    }

    @Test
    public void getFriends() {

    }

    @Test
    public void unfriendSomeone() {

    }

    @Test
    public void updateFriendAlias() {

    }

}
