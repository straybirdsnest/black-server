package org.team10424102.blackserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.blackserver.models.FriendshipApplication;
import org.team10424102.blackserver.models.MembershipApplication;

@Repository
public interface MembershipApplicationRepo extends PagingAndSortingRepository<MembershipApplication, Long> {
}
