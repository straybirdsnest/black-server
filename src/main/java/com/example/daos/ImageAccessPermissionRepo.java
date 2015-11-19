package com.example.daos;

import com.example.models.Image;
import com.example.models.ImageAccessPermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageAccessPermissionRepo extends CrudRepository<ImageAccessPermission, Long> {
    List<ImageAccessPermission> findByImage(Image image);
}
