package com.example.daos;

import com.example.models.Academy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AcademyRepo extends PagingAndSortingRepository<Academy, Integer> {
    Academy findOneByName(String name);
}
