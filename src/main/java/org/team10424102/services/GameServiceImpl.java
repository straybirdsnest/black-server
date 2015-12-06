package org.team10424102.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team10424102.daos.GameRepo;
import org.team10424102.models.Game;

/**
 * Created by sk on 15-12-5.
 */
@Service
public class GameServiceImpl implements GameService {

  @Autowired GameRepo gameRepo;

  @Override
  public Game getGame(String name) {
    return gameRepo.findOneByName(name);
  }
}
