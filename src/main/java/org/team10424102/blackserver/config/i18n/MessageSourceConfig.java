package org.team10424102.blackserver.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sk on 15-12-6.
 */
@Configuration
public class MessageSourceConfig {

  @Bean
  public MessageSource messageSource() {
    MultipleReloadableResourceBundleMessageSource messageSource = new MultipleReloadableResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
