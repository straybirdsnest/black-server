package com.example;

import com.example.models.profiles.Academy;
import com.example.models.profiles.College;
import com.example.models.core.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by yy on 9/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class DataModelTest {

    @Autowired
    EntityManager em;

    @Test
    public void testHibernateORMapping() {

    }

    @Test
    @Transactional
    @Rollback(false)
    public void saveUser() {
        UserProfile s = new UserProfile();

        s.setName("李铁蛋");
        s.setUsername("test1");
        s.setPassword("1qasde32w");

        College c = new College();
        c.setName("哈尔滨佛教大学");

        Academy a = new Academy();
        a.setName("第二炮兵学院");
        a.setCollege(c);

        s.setCollege(c);

        s.setAcademy(a);

        Session session = em.unwrap(Session.class);
        session.save(s);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void deleteUser() {
        Session session = em.unwrap(Session.class);

        Query q = session.createQuery("from UserProfile s where s.name like '李%'");

        List<User> users = q.list();

        for (User u : users) {
            System.out.println("已删除用户 (ID: " + u.getId() + ")");
            session.delete(u);
        }
    }

    @Test
    public void resetDatabase() {

    }
}
