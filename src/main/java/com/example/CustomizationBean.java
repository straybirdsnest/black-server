package com.example;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.addErrorPages(new ErrorPage(IOException.class, "/fuckjetty")
//        , new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
    }
}
