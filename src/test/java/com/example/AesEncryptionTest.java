package com.example;

import com.example.utils.CompressionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by yy on 9/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class AesEncryptionTest {

    @Autowired SecretKeySpec key;

    @Test
    public void test1() throws Exception{
        //String password = "这是加密密码";
        String content = "这是要加密的内容这是要加密的内容这是要加密的内容这是要加密的内容这是要加密的内容这是要加密的内容这是要加密的内容这是要加密的内容";
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128, new SecureRandom(password.getBytes()));
        //SecretKey secretKey = kgen.generateKey();
        //byte[] enCodeFormat = secretKey.getEncoded();
        //SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        System.out.println(Base64.getEncoder().encodeToString(result));
        byte[] zipResult = CompressionUtils.compress(result);
        String zipResult64 = Base64.getEncoder().encodeToString(zipResult);
        System.out.println(Base64.getEncoder().encodeToString(zipResult));
        byte[] zipzipResult = CompressionUtils.compress(zipResult64.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(zipzipResult));
    }




}
