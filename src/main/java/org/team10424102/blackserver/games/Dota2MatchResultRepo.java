package org.team10424102.blackserver.games;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Dota2MatchResultRepo extends CrudRepository<Dota2MatchResult, Long> {
}
