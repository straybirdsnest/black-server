package com.example.daos;

import com.example.models.Game;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends PagingAndSortingRepository<Game, Integer> {
    Game findOneByName(String name);
}
