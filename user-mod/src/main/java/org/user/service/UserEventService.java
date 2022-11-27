package org.user.service;

import lombok.extern.log4j.Log4j2;
import org.data.model.entity.User;
import org.data.model.events.UserEvent;
import org.data.model.events.UserEventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.UserDao;

@Service
@Log4j2
public class UserEventService {

    @Autowired
    private UserDao userDao;

    public UserEventLog create(User user) {
        return userEvent(user, UserEvent.CREATE);
    }

    public UserEventLog get(User user) {
        return userEvent(user, UserEvent.ACCESS);
    }

    public UserEventLog userEvent(User user, UserEvent userEvent) {
        return UserEventLog.builder()
                .userId(user.getId())
                .userUUID(user.getUserId())
                .createdAt(user.getCreatedAt())
                .userEvent(userEvent)
                .build();
    }

    public void save(UserEventLog userEventLog) {
        userDao.save(userEventLog)
                .doOnError(error -> log.info(error))
                .subscribe();
    }
}
