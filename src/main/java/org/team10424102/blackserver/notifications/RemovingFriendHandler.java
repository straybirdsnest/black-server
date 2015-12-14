package org.team10424102.blackserver.notifications;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.team10424102.blackserver.daos.FriendshipApplicationRepo;
import org.team10424102.blackserver.daos.NotificationRepo;
import org.team10424102.blackserver.daos.UserRepo;
import org.team10424102.blackserver.models.Friendship;
import org.team10424102.blackserver.models.FriendshipApplication;
import org.team10424102.blackserver.models.Notification;
import org.team10424102.blackserver.models.User;

import java.util.Calendar;

@Component
@HandleNotificationType(Notification.FRIEND_REMOVE)
public class RemovingFriendHandler implements NotificationHandler {

    @Autowired NotificationRepo notificationRepo;

    @Autowired UserRepo userRepo;

    public void unfriend(User applicant,User target) {
        if (applicant == null || target == null) return;

        if (applicant.equals(target)) return;

        boolean removed = applicant.getFriendshipSet().removeIf(e -> e.getFriend().equals(target));

        if (!removed) return;

        target.getFriendshipSet().removeIf(e -> e.getFriend().equals(applicant));

        Notification notification = new Notification();
        notification.setTarget(target);
        notification.setType(Notification.FRIEND_REMOVE);
        notification.setDataId(applicant.getId());
        notificationRepo.save(notification); // 发出友尽通知
    }


    @Override
    public Notification inflateData(Notification notification) {
        Long id = notification.getDataId();
        User user = userRepo.findOne(id);
        notification.setData(user);
        return notification;
    }

    @Override
    public void handleReply(Notification notification, int reply) {
        // no-op
    }










}
