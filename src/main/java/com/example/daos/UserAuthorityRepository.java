package com.example.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserAuthorityRepository extends PagingAndSortingRepository<UserAuthority, Long> {
    UserAuthority findOneByAuthorityName(String authorityName);
}
