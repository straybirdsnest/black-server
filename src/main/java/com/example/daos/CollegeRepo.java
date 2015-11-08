package com.example.daos;

import com.example.models.College;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CollegeRepo extends PagingAndSortingRepository<College, Integer> {
    College findOneByName(String name);
}
