package com.example.config.converters.json;

import com.example.models.Image;
import com.example.services.ImageService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageDeserializer extends JsonDeserializer<Image> {
    private static final Logger logger = LoggerFactory.getLogger(ImageDeserializer.class);

    @Autowired ImageService imageService;

    @Override
    public Image deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
        String token = jp.getText();
        logger.trace("获得图片的访问 token = " + token);
        return imageService.getLazyImageFromAccessToken(token);
    }
}
