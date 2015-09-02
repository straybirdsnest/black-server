package com.example;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.junit.Test;

import java.security.Key;

/**
 * Created by yy on 8/30/15.
 */
public class JwtTest {
    @Test
    public void simple() throws Exception{
        // 一个随机的 key
        Key key = new AesKey(ByteUtil.randomBytes(16));
        System.out.println(key.toString());

        // 加密
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload("Hello World!");
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String serializedJwe = jwe.getCompactSerialization();
        System.out.println("Serialized Encrypted JWE: " + serializedJwe);

        // 解密
        jwe = new JsonWebEncryption();
        jwe.setKey(key);
        jwe.setCompactSerialization(serializedJwe);
        System.out.println("Payload: " + jwe.getPayload());
    }
}
