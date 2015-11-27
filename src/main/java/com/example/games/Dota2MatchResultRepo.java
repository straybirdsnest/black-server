package com.example.games;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Dota2MatchResultRepo extends CrudRepository<Dota2MatchResult, Long> {
}
