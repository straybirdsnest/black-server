package org.team10424102.services.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.team10424102.daos.ImageRepo;
import org.team10424102.models.Image;
import org.team10424102.services.ImageService;

import java.util.ArrayList;
import java.util.List;

@PostExtensionIdentifier("image")
@Component
public class ImagePostExtension implements PostExtension {

    @Autowired ImageRepo imageRepo;

    @Autowired ImageService imageService;

    @Override
    public Object getData(String stub) {
        String[] ids = stub.split(",");
        List<String> imageTokens = new ArrayList<>();
        for (String id : ids) {
            Long imageId = Long.parseLong(id);
            Image image = imageRepo.findOne(imageId);
            imageTokens.add(imageService.generateAccessToken(image));
        }
        return imageTokens;
    }
}
