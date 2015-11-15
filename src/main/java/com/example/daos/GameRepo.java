package com.example.daos;

import com.example.models.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRepo extends PagingAndSortingRepository<Game, Integer> {
    Game findOneByName(String name);
}
