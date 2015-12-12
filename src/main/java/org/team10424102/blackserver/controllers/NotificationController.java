package org.team10424102.blackserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.team10424102.blackserver.App;
import org.team10424102.blackserver.config.security.CurrentUser;
import org.team10424102.blackserver.daos.FriendshipApplicationRepo;
import org.team10424102.blackserver.daos.NotificationRepo;
import org.team10424102.blackserver.daos.UserRepo;
import org.team10424102.blackserver.models.Friendship;
import org.team10424102.blackserver.models.FriendshipApplication;
import org.team10424102.blackserver.models.Notification;
import org.team10424102.blackserver.models.User;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(App.API_NOTIFICATION)
public class NotificationController {

    @Autowired NotificationRepo notificationRepo;

    @Autowired FriendshipApplicationRepo friendshipApplicationRepo;

    @Autowired UserRepo userRepo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Notification> getNotifications(@CurrentUser User user) {
        Iterator<Notification> i = notificationRepo.findAllByTarget(user).iterator();
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(i, Spliterator.ORDERED), false)
                .map(e -> {
                    switch (e.getType()) {
                        case Notification.FRIEND_ADD:
                            handleFriendAdd(e);
                            break;
                        case Notification.FRIEND_REMOVE:
                            handleFriendRemove(e);
                            break;
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    private void handleFriendAdd(Notification n) {
        Long id = n.getDataId();
        FriendshipApplication application = friendshipApplicationRepo.findOne(id);
        n.setData(application);
    }

    private void handleFriendRemove(Notification n) {
        Integer id = n.getDataId().intValue();
        User user = userRepo.findOne(id);
        n.setData(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Transactional
    private void handleReply(@PathVariable Long id, @RequestParam int type, @RequestParam int reply) {
        Notification notification = notificationRepo.findOne(id);
        if (notification == null) return;
        switch (type) {
            case Notification.FRIEND_ADD:
                if (reply == Notification.REPLY_YES) {

                    handleFriendRemove(notification);
                    FriendshipApplication application = (FriendshipApplication)notification.getData();

                    User applicant = application.getApplicant();
                    User target = application.getTarget();

                    Friendship friendship = new Friendship();
                    friendship.setUser(applicant);
                    friendship.setFriend(target);
                    applicant.getFriendshipSet().add(friendship);
                    userRepo.save(applicant);

                    friendship = new Friendship();
                    friendship.setUser(target);
                    friendship.setFriend(applicant);
                    target.getFriendshipSet().add(friendship);
                    userRepo.save(target);
                }
                break;
            case Notification.FRIEND_REMOVE:
                break;
        }
    }



}
