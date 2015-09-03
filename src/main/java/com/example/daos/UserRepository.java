package com.example.daos;

import com.example.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yy on 8/30/15.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    List<User> findByUsername(String username);

}
