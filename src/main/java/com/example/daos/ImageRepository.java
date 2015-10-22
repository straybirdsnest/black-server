package com.example.daos;

import com.example.models.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yy on 8/30/15.
 */
@Repository
@Transactional
public interface ImageRepository extends PagingAndSortingRepository<Image, String> {

}
