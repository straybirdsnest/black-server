package org.team10424102.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.models.College;
import org.team10424102.models.Post;
import org.team10424102.models.PostLike;
import org.team10424102.models.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepo extends PagingAndSortingRepository<Post, Long> {

    List<Post> findByCommentativeFalseAndSenderProfileCollege(College college, Pageable pageable);

    List<Post> findByCommentativeFalseAndSenderIn(Collection<User> users, Pageable pageable);

    List<Post> findByCommentativeFalseAndSender(User sender, Pageable pageable);

    List<Post> findCommentsById(Long id, Pageable pageable);

    List<PostLike> findLikesById(Long id, Pageable pageable);
}
