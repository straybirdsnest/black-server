package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;


public class ApplicationLifecycleManager implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLifecycleManager.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("服务器成功启动，撒花 (￣▽￣)o∠※PAN!=.:*:'☆.:*:'★':*");
        initDb(event.getApplicationContext());
    }

    @Transactional
    private void initDb(ApplicationContext context) {
//        EntityManagerFactory factory = context.getBean(EntityManagerFactory.class);
//        EntityManager em = factory.createEntityManager();
//        Session session = em.unwrap(Session.class);
//        //User user = new User("999999");
//        session.save(user);
    }
}
