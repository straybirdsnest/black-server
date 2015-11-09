package com.example.services;

import com.example.daos.ImageRepo;
import com.example.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageService {

    @Autowired
    ImageRepo imageRepo;

    public boolean canAccessImage(String q) {
        // TODO always return true
        return true;
    }

    public String generateAccessToken(Image image){
        // TODO generate image access token
        String token = "token-" + image.getId();
        image.setAccessToken(token);
        imageRepo.save(image);
        return token;
    }

    public byte[] convertToPNGImageData(byte[] bytes){
        // TODO convert to png image data
        return bytes;
    }

}
