package com.example.daos;

import com.example.models.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ActivityRepo extends PagingAndSortingRepository<Activity, Integer> {

}
