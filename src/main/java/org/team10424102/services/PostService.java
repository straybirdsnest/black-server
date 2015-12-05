package org.team10424102.services;

import org.springframework.data.domain.Pageable;
import org.team10424102.models.Post;
import org.team10424102.models.PostLike;

import java.util.List;

public interface PostService {
    void registerPostExtention(Class cls);

    void unregisterPostExtention(Class cls);

    List<Post> getSchoolMatchPosts(Pageable pageable);

    List<Post> getFriendsMatchPosts(Pageable pageabl);

    List<Post> getFocusesMatchPosts(Pageable pageabl);

    List<Post> getMyMatchPosts(Pageable pageabl);

    List<Post> getCommentPosts(Pageable pageabl, long postId);

    void likePost(long postId);

    void unlikePost(long postId);

    void commentPost(long postId, String content);

    void createPost(String content);

    void deletePost(long postId);

    List<PostLike> getLikes(long postId, Pageable pageable);

}
