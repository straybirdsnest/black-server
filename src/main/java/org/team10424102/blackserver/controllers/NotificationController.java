package org.team10424102.blackserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.team10424102.blackserver.App;
import org.team10424102.blackserver.config.security.CurrentUser;
import org.team10424102.blackserver.daos.FriendshipApplicationRepo;
import org.team10424102.blackserver.daos.NotificationRepo;
import org.team10424102.blackserver.daos.UserRepo;
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
        Iterator<Notification> i = notificationRepo.findAllByUser(user).iterator();
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(i, Spliterator.ORDERED), false)
                .map(e -> {
                    switch (e.getType()) {
                        case FRIEND_ADD:
                            handleFriendAdd(e);
                            break;
                        case FRIEND_REMOVE:
                            handleFriendRemove(e);
                            break;
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    private void handleFriendAdd(Notification n) {
        String extra = n.getExtra();
        Long id = Long.parseLong(extra);
        FriendshipApplication application = friendshipApplicationRepo.findOne(id);
        n.setData(application);
    }

    private void handleFriendRemove(Notification n) {
        String extra = n.getExtra();
        Integer id = Integer.parseInt(extra);
        User user = userRepo.findOne(id);
        n.setData(user);
    }



}
