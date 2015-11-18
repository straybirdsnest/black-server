package com.example.services;

import com.example.models.Image;
import org.jetbrains.annotations.Nullable;

public interface ImageService {
    boolean canAccessImage(Image image);

    String generateAccessToken(Image image);

    @Nullable
    Long getImageIdFromAccessToken(String accessToken);

    Image getLazyImageFromAccessToken(String accessToken);

    DefaultImageService getDefault();

    Image createAndSaveImage(byte[] data, String tags);
}
