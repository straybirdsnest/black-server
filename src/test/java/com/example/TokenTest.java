package com.example;

import com.example.config.restful.Token;
import com.example.models.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yy on 9/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class TokenTest {

    @Test
    public void test1() throws Exception{
        Token token = new Token();
        token.setUserId(123);
        Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
        calendar.add(Calendar.SECOND, 30);
        token.setExpire(calendar.getTime());
        token.setIp(InetAddress.getByName("192.168.1.123"));
        token.getRoles().add(new Role("ADMIN"));
        token.getRoles().add(new Role("VIP"));

        String s = token.toString();

        Token newToken = token.parse(s);

        System.out.println(newToken.getExpire());
        System.out.println(newToken.getIp());
        System.out.println(newToken.getRoles());
        System.out.println(newToken.getUserId());
    }



}
