package org.team10424102.blackserver.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team10424102.blackserver.App;
import org.team10424102.blackserver.models.Game;
import org.team10424102.blackserver.config.json.Views;
import org.team10424102.blackserver.services.GameService;

import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by sk on 15-12-5.
 */
@RestController
public class GameController implements MessageSourceAware {

  @Autowired GameService gameService;

  private MessageSource messageSource;

  /**
   * 获取活动的详细信息
   */
  @RequestMapping(value = App.API_GAME, method = GET)
  @JsonView(Views.Game.class)
  public Game getGame(@RequestParam String identifier, @RequestHeader("Accept-Language") Locale locale) {
    Game game = gameService.getGame(identifier);
    game.setLocalizedName(messageSource.getMessage("game." + game.getIdentifier(), null, locale));
    return game;
  }

  @Override
  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
}
