package org.team10424102.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.team10424102.models.Academy;

@Repository
@Transactional
public interface AcademyRepo extends PagingAndSortingRepository<Academy, Integer> {
    Academy findOneByName(String name);
}
