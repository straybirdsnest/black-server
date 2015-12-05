package org.team10424102.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.models.PostLike;
import org.team10424102.models.User;

@Repository
public interface LikesRepo extends CrudRepository<PostLike, Long> {
    PostLike findOneByPostIdAndUser(Long postId, User user);
}
