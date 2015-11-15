package com.example.daos;

import com.example.models.Group;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepo extends PagingAndSortingRepository<Group, Integer> {
}
