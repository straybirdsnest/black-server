package com.example;

import com.example.daos.UserRepo;
import com.example.models.User;
import com.example.services.TokenService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class ImageTest{

    @Autowired TokenService tokenService;

    @Autowired UserRepo userRepo;

    @Autowired WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpAll() {
        //DevHelper.initDb(null);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    /**
     * 测试上传图片
     */
    @Test
    public void uploadImage() throws Exception{
        User u = userRepo.findOne(1);
        String token = tokenService.generateToken(u);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Token", token);
        MockMultipartFile image =
                new MockMultipartFile("file", "picture.png", "image/png",
                        getClass().getResourceAsStream("/dev/data/avatars/wnm.png"));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.fileUpload("/api/image")
                        .file(image)
                        .headers(headers))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("~")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
}
