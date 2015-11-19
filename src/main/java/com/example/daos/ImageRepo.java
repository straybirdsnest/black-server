package com.example.daos;

import com.example.models.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepo extends CrudRepository<Image, Long> {
    Image findOneByTags(String tags);
    Image findOneByHash(String hash);
}
