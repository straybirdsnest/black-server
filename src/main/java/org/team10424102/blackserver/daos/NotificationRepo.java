package org.team10424102.blackserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.blackserver.models.Notification;
import org.team10424102.blackserver.models.User;

import java.util.List;

@Repository
public interface NotificationRepo extends PagingAndSortingRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
}
