package com.example.models.proxies;

import com.example.models.Image;
import com.example.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageProxy {

    private static final Logger logger = LoggerFactory.getLogger(ImageProxy.class);

    private ImageService imageService;

    public void setImageService(ImageService imageService){
        this.imageService = imageService;
    }

    public String getAccessToken(Image image){
        logger.debug("image input"+ image);
        logger.debug("imageService "+imageService);
        return imageService.generateAccessToken(image);
    }

}
