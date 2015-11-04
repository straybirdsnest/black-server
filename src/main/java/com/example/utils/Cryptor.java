package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cryptor {
    static final Logger log = LoggerFactory.getLogger(Cryptor.class);
    private static Cipher cipher;
    private static SecretKey secretKey;
    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();
            log.debug("本次生成的 AES 密钥为：" + secretKeyToString(secretKey));
            cipher = Cipher.getInstance("AES");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param data
     * @return 当出现异常的时候返回空字符串
     */
    public static String encrypt(byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(data);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedByte);
        }catch (Exception e){
            return "";
        }
    }

    /**
     * @param encryptedText
     * @return 当出现异常的时候返回 null
     */
    public static byte[] decrypt(String encryptedText) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedTextByte = decoder.decode(encryptedText);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedTextByte);
        } catch (Exception e){
            return null;
        }
    }

    private static String secretKeyToString(SecretKey key) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private static SecretKey stringToSecretKey(String str) {
        byte[] decodedKey = Base64.getDecoder().decode(str);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
