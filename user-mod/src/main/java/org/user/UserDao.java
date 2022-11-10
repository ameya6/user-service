package org.user;

import org.data.dao.RedisDao;
import org.data.model.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {

    @Autowired
    private Session session;

    @Autowired
    private RedisDao<User> userRedisDao;

    static final long DAY_SECONDS = 86_000L;

    @Transactional
    public void save(User user) {
        session.save(user);
        redisSave(user);
        //session.flush();
    }

    public void redisSave(User user) {
        redisSave(user, false);
    }

    public void redisSave(User user, boolean expire) {
        String userKey = User.KEY+user.getUserId();
        userRedisDao.save(userKey, user);
        if(expire)
            userRedisDao.expire(userKey, DAY_SECONDS * 7);
    }
}
