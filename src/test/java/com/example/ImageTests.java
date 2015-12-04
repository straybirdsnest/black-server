package com.example;

import com.example.daos.ImageRepo;
import com.example.models.Image;
import com.example.utils.Cryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertTrue;

public class ImageTests extends BaseTests {

    @Autowired ImageRepo imageRepo;

    @Test
    public void md5() {
        Image image = imageRepo.findOne(1L);
        String md5 = Cryptor.md5_hex(image.getData());
        assertTrue(md5.equals(image.getHash()));
    }










}
