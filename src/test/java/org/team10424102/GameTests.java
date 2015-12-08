package org.team10424102;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.team10424102.App.API_GAME;

public class GameTests extends BaseTests {

    @Test
    public void getGame() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_GAME).header("X-Token", token)
                .header("Accept-Language", "zh_CN")
                .param("identifier", "MINECRAFT")
        )
                .andExpect(status().isOk())
                .andReturn();
        printFormatedJsonString(result);
    }

    @Test
    public void getGameEnUS() throws Exception {
        String token = getToken();
        MvcResult result = mockMvc.perform(get(API_GAME).header("X-Token", token)
                .header("Accept-Language", "en_US")
                .param("identifier", "MINECRAFT")
        )
                .andExpect(status().isOk())
                .andReturn();
        printFormatedJsonString(result);
    }

}
