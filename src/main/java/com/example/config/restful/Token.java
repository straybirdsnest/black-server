package com.example.config.restful;

import com.example.models.Role;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by yy on 9/12/15.
 */
public class Token {

    private int userId;

    private Date expire;

    private InetAddress ip;

    private List<Role> roles = new ArrayList<>();

    private static SecretKeySpec key;

    private static Cipher encryptCipher;

    private static Cipher decryptCipher;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Token parse(String encryptedString){
        try {
            byte[] bytes64 = Base64.getDecoder().decode(encryptedString);
            byte[] bytes = decryptCipher.doFinal(bytes64);
            return mapper.readValue(bytes, Token.class);
        } catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this);
            byte[] byteContent = json.getBytes();
            byte[] result = encryptCipher.doFinal(byteContent);
            return Base64.getEncoder().encodeToString(result);
        } catch (JsonProcessingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static SecretKeySpec getKey() {
        return key;
    }

    public static void setKey(SecretKeySpec key) {
        Token.key = key;

        try {
            encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }
}
