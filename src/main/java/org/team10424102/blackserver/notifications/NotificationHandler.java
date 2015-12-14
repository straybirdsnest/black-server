package org.team10424102.blackserver.notifications;

import org.team10424102.blackserver.models.Notification;

public interface NotificationHandler {
    Notification inflateData(Notification notification);
    void handleReply(Notification notification, int reply);
}
