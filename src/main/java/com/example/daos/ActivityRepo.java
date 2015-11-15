package com.example.daos;

import com.example.models.Activity;
import com.example.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ActivityRepo extends PagingAndSortingRepository<Activity, Integer> {
    Page<Activity> findByPromoter(User user, Pageable pageable);
}
