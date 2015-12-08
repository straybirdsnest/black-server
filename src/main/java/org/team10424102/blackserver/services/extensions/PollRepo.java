package org.team10424102.blackserver.services.extensions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepo extends CrudRepository<Poll, Long> {
}
