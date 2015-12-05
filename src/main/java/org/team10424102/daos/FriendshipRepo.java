package org.team10424102.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.models.Friendship;

@Repository
public interface FriendshipRepo extends PagingAndSortingRepository<Friendship, Integer> {
}
