package com.example.services;

import com.example.models.Post;
import com.example.models.PostLike;
import org.springframework.data.domain.Pageable;

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
