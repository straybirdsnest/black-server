package com.example.daos;

import com.example.models.core.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yy on 8/30/15.
 */
@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findByNickname(String nickname);

    User findOneByNickname(String nickname);

}
