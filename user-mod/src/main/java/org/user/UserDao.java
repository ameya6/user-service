package org.user;

import org.data.model.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {

    @Autowired
    private Session session;

    @Transactional
    public void save(User user) {
        session.save(user);
        //session.flush();
    }
}
