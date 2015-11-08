package com.example.services;

import com.example.models.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageService {

    public boolean canAccessImage(String q) {
        // TODO always return ture
        return true;
    }

    public String generateAccessToken(Image image){
        // TODO generate image access token
        return "token-" + image.getId();
    }

    public byte[] convertToPNGImageData(byte[] bytes){
        // TODO convert to png image data
        return bytes;
    }

}
