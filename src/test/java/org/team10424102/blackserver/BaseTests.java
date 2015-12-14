package org.team10424102.blackserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.team10424102.blackserver.config.security.TokenAuthenticationFilter;

import javax.servlet.Filter;
import javax.transaction.Transactional;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.team10424102.blackserver.App.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") // random port
@Transactional
public class BaseTests {
    protected MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired Filter springSecurityFilterChain;
    private String token;
    private String tokenA;
    private String tokenB;
    public final String UUID_PATTERN = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public final String AUTH_HEADER = TokenAuthenticationFilter.HTTP_HEADER;
    public static final int USER_ID = 2;
    public static final int USER_A_ID = 3;
    public static final int USER_B_ID = 4;
    public static final int USER_C_ID = 5;

    protected RestTemplate rest = new TestRestTemplate();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    @BeforeClass
    public static void beforeClass() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }

    protected void printFormatedJsonString(MvcResult result) throws Exception {
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }

    protected String getToken() throws Exception {
        if (token != null) return token;
        return token = getTokenInternal("18921273088");
    }

    private String getTokenInternal(String phone) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(API_USER + "/token")
                .param("phone", phone)
                .param("vcode", "1234"))
                .andExpect(status().isOk()).andReturn();
        String tokenJson = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(tokenJson);
        token = obj.getString("token");
        return token;
    }

    protected String getTokenA() throws Exception {
        if (tokenA != null) return tokenA;
        return tokenA = getTokenInternal("18921273089");
    }

    protected String getTokenB() throws Exception {
        if (tokenB != null) return tokenB;
        return tokenB = getTokenInternal("18921273090");
    }
}
