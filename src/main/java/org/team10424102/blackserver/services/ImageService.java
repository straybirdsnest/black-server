package org.team10424102.blackserver.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.team10424102.blackserver.models.Image;

public interface ImageService {

    /**
     * 获得系统默认图片
     */
    @NotNull
    DefaultImage getDefault();
}
