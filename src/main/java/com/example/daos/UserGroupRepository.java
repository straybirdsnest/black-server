package com.example.daos;

import com.example.models.UserGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, Long> {
    UserGroup findOneByGroupName(String groupName);
}
