package org.team10424102.blackserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.blackserver.models.Membership;
import org.team10424102.blackserver.models.MembershipApplication;

@Repository
public interface MembershipRepo extends PagingAndSortingRepository<Membership, Long> {
}
