package com.example.daos;

import com.example.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yy on 8/30/15.
 */
@Repository
public interface ImageRepository extends CrudRepository<Image, String> {

}
