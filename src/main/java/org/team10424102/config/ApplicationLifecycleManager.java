package org.team10424102.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.team10424102.games.Dota2PostExtension;
import org.team10424102.services.PostService;
import org.team10424102.services.extensions.ImagePostExtension;
import org.team10424102.services.extensions.PollPostExtension;

@Component
public class ApplicationLifecycleManager{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLifecycleManager.class);

    @Autowired ApplicationContext context;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        logger.info("服务器成功启动，撒花 (￣▽￣)o∠※PAN!=.:*:'☆.:*:'★':*");
        PostService postService = event.getApplicationContext().getBean(PostService.class);
        postService.registerPostExtention(Dota2PostExtension.class);
        postService.registerPostExtention(ImagePostExtension.class);
        postService.registerPostExtention(PollPostExtension.class);
    }
}
