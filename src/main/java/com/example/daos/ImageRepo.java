package com.example.daos;

import com.example.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends CrudRepository<Image, Long> {
    Image findOneByTags(String tags);
    Image findOneByHash(String hash);
}
