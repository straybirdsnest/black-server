package org.team10424102.blackserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.team10424102.blackserver.models.Academy;

@Repository
public interface AcademyRepo extends PagingAndSortingRepository<Academy, Integer> {
    Academy findOneByName(String name);
}
