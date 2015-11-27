package com.example.daos;

import com.example.models.PostLike;
import com.example.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends CrudRepository<PostLike, Long> {
    PostLike findOneByPostIdAndUser(Long postId, User user);
}
