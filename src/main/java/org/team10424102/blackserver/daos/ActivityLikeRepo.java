package org.team10424102.blackserver.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.blackserver.models.ActivityLike;
import org.team10424102.blackserver.models.User;

/**
 * Created by sk on 15-12-12.
 */
@Repository
public interface ActivityLikeRepo extends CrudRepository<ActivityLike, Long> {

    ActivityLike findOneByActivityIdAndUser(Long activityId, User user);
}
