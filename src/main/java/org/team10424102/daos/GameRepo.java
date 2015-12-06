package org.team10424102.daos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.team10424102.models.Game;

@Repository
public interface GameRepo extends PagingAndSortingRepository<Game, Integer> {

    @Query("select g from Game g where g.name = ?1")
    Game findOneByName(String name);
}
