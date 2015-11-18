package com.example.daos;

import com.example.models.Friendship;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepo extends PagingAndSortingRepository<Friendship, Integer> {
}
