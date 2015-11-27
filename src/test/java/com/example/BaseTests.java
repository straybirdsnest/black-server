package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.transaction.Transactional;
import java.util.TimeZone;

import static com.example.App.API_TOKEN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") // random port
@Transactional
public class BaseTests {
    protected MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired Filter springSecurityFilterChain;

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    protected void printFormatedJsonString(MvcResult result) throws Exception{
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }

    protected String getToken() throws Exception{
        MvcResult result = mockMvc.perform(get(API_TOKEN).param("phone", "123456789").param("vcode", "1234"))
                .andExpect(status().isOk()).andReturn();
        String tokenJson = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenJson);
        return obj.getString("token");
    }
}
