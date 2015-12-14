package org.team10424102.blackserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team10424102.blackserver.daos.ActivityLikeRepo;
import org.team10424102.blackserver.daos.ActivityRecommendationRepo;
import org.team10424102.blackserver.daos.PostRepo;
import org.team10424102.blackserver.models.*;
import org.team10424102.blackserver.daos.ActivityRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired ActivityRepo activityRepo;

    @Autowired UserService userService;

    @Autowired ActivityRecommendationRepo activityRecommendationRepo;

    @Autowired ActivityLikeRepo activityLikeRepo;

    @Autowired PostRepo postRepo;

    @Override
    public List<Activity> getRecommendedActivities(Pageable pageable) {
        Iterable<ActivityRecommendation> i = activityRecommendationRepo.findAll(pageable);
        List<Activity> activities = new ArrayList<>();
        for (ActivityRecommendation r : i) {
            activities.add(r.getActivity());
        }
        return activities;
    }

    @Override
    public List<Activity> getSameSchoolActivities(Pageable pageable) {
        User user = userService.getCurrentUser();
        return activityRepo.findByPromoterCollege(user.getCollege(), pageable);
    }

    @Override
    public List<Activity> getFriendsActivities(Pageable pageable) {
        User user = userService.getCurrentUser();
        Set<User> friends = user.getFriendshipSet().stream().map(Friendship::getFriend).collect(Collectors.toSet());
        return activityRepo.findByPromoterIn(friends, pageable);
    }

    @Override
    public List<Activity> getFocusesActivities(Pageable pageable) {
        User user = userService.getCurrentUser();
        Set<User> focuses = user.getFocuses();
        return activityRepo.findByPromoterIn(focuses, pageable);
    }

    @Override
    public List<Activity> getMyActivities(Pageable pageable) {
        User user = userService.getCurrentUser();
        return activityRepo.findByPromoter(user, pageable);
    }

    @Override
    public void createActivity(Activity activity) {
        activityRepo.save(activity);
    }

    @Override
    public void deleteActivity(long activityId) {
        Activity activity = activityRepo.findOne(activityId);
        activity.getComments().clear();
        activity.getPhotos().clear();
        activityRepo.delete(activityId);
    }

    @Override
    public Activity findOne(long activityId) {
        return activityRepo.findOne(activityId);
    }

    @Override
    public void likeActivity(long activityId) {
        User user = userService.getCurrentUser();
        ActivityLike like = new ActivityLike();
        like.setUser(user);
        like.setActivity(activityRepo.findOne(activityId));
        activityLikeRepo.save(like);
    }

    @Override
    public void unlikeActivity(long activityId) {
        User user = userService.getCurrentUser();
        ActivityLike like = activityLikeRepo.findOneByActivityIdAndUser(activityId, user);
        activityLikeRepo.delete(like);
    }

    @Override
    public List<Post> getComments(Pageable pageabl, long postId) {
        return activityRepo.findCommentsById(postId, pageabl);
    }

    @Override
    @Transactional
    public void saveComment(long activityId, String content) {
        User user = userService.getCurrentUser();
        Post comment = new Post();
        comment.setCreationTime(new Date());
        comment.setSender(user);
        comment.setContent(content);
        comment.setCommentative(true);

        postRepo.save(comment);

        Activity activity = activityRepo.findOne(activityId);
        activity.getComments().add(comment);

        activityRepo.save(activity);
    }

    @Override
    public void deleteComment(long activityId, long commentId) {
        User user = userService.getCurrentUser();
        Post comment = postRepo.findOne(commentId);
        if (comment.getSender().equals(user)) {
            Activity activity = activityRepo.findOne(activityId);
            activity.getComments().remove(comment);
            activityRepo.save(activity);

            comment.getComments().clear();
            postRepo.delete(comment);
        }
    }
}
