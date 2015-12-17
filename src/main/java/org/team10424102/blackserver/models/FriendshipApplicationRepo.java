package org.team10424102.blackserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.team10424102.blackserver.models.Academy;
import org.team10424102.blackserver.models.FriendshipApplication;

@Repository
public interface FriendshipApplicationRepo extends PagingAndSortingRepository<FriendshipApplication, Long> {
}
