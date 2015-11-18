package com.example.services.impl;

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

    }


    @NotNull
    public Image avatar() {
        return null;
    }

    @NotNull
    public Image background() {
        return null;
    }

    @NotNull
    public Image cover() {
        return null;
    }
}
