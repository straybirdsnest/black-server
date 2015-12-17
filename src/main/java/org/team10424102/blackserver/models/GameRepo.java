package org.team10424102.blackserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.blackserver.models.Game;

@Repository
public interface GameRepo extends PagingAndSortingRepository<Game, Integer> {

    Game findOneByNameKey(String nameKey);
}
