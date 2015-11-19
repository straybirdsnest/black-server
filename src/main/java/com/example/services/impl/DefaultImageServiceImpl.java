package com.example.services.impl;

import com.example.App;
import com.example.daos.ImageRepo;
import com.example.models.Image;
import com.example.services.DefaultImageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultImageServiceImpl implements DefaultImageService {
    private Image avatar;
    private Image background;
    private Image cover;

    @Autowired
    public DefaultImageServiceImpl(ImageRepo imageRepo){
        //loadDefaultImages(imageRepo);
    }

    public void loadDefaultImages(ImageRepo imageRepo){
        avatar = imageRepo.findOneByTags(App.DEFAULT_AVATAR_TAG);
        background = imageRepo.findOneByTags(App.DEFAULT_BACKGROUND_TAG);
        cover = imageRepo.findOneByTags(App.DEFAULT_COVER_TAG);
    }

    @NotNull
    public Image avatar() {
        return avatar;
    }

    @NotNull
    public Image background() {
        return background;
    }

    @NotNull
    public Image cover() {
        return cover;
    }
}
