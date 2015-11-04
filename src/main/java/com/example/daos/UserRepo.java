package com.example.daos;

import com.example.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends PagingAndSortingRepository<User, Integer> {

    User findOneByPhone(String phone);

}
