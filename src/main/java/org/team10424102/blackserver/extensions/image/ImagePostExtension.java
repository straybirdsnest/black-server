package org.team10424102.blackserver.extensions.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.team10424102.blackserver.daos.ImageRepo;
import org.team10424102.blackserver.models.Image;
import org.team10424102.blackserver.services.ImageService;
import org.team10424102.blackserver.extensions.PostExtension;

import java.util.ArrayList;
import java.util.List;

@Component("image")
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
