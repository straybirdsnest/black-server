package org.team10424102.blackserver.dev;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Created by sk on 15-12-8.
 */
public class I18nBaseCode implements MessageSourceAware {

  private MessageSource messageSource;

  public String getText(String name) {
    return messageSource.getMessage(name, null, LocaleContextHolder.getLocale());
  }

  @Override
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
}
