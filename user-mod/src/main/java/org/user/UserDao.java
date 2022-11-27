package org.user;

import org.data.dao.RedisDao;
import org.data.dao.UserEventRepository;
import org.data.model.entity.User;
import org.data.model.events.UserEventLog;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class UserDao {

    @Autowired
    private Session session;

    @Autowired
    private RedisDao<User> userRedisDao;

    @Autowired
    UserEventRepository eventRepository;

    static final long DAY_SECONDS = 86_000L;

    @Transactional("transactionManager")
    public void save(User user) {
        session.save(user);
        redisSave(user);
        //session.flush();
    }

    public void redisSave(User user) {
        redisSave(user, false);
    }

    public void redisSave(User user, boolean expire) {
        String userKey = User.KEY+user.getId();
        userRedisDao.save(userKey, user);
        if(expire)
            userRedisDao.expire(userKey, DAY_SECONDS * 7);
    }

    public User get(Long id) {
        User user = redisGet(id);
        if(user != null)
            return user;
        user = session.find(User.class, id);
        redisSave(user, true);
        return user;
    }

    public User redisGet(Long id) {
        return userRedisDao.get(User.KEY + id, User.class);
    }

    public Mono<UserEventLog> save(UserEventLog eventLog) {
        return eventRepository.save(eventLog);
    }
}
