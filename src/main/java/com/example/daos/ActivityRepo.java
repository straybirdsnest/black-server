package com.example.daos;

import com.example.models.Activity;
import com.example.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface ActivityRepo extends PagingAndSortingRepository<Activity, Integer> {
    Set<Activity> findByPromoter(User user);
}
