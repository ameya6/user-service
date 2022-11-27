package org.user.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.data.model.entity.User;
import org.data.model.entity.UserStatus;
import org.data.model.events.UserEventLog;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user.UserDao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserEventService eventService;

    private final static Integer MILLION = 1000000;

    public UserResponse create(UserRequest userRequest) {
        UserResponse response = null;
        User user = createUser(userRequest);
        UserEventLog userEventLog = eventService.create(user);
        userEventLog.setProcessStartTime(LocalDateTime.now());
        try {
            userDao.save(user);
            response = createUserResponse(user);
            response.setMessage("User " + user.getFirstName() + " " + user.getLastName() + " created successfully");
            //log.info(user + " " + response);
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            log.error(user + " " + userRequest);
            userEventLog.setExceptionMessage(e.getMessage());
        } finally {
            userEventSave(userEventLog);
            return response;
        }
    }

    public UserResponse get(Long id) {
        LocalDateTime start = LocalDateTime.now();
        User user = userDao.get(id);
        UserEventLog userEventLog = eventService.get(user);
        userEventLog.setProcessStartTime(start);
        UserResponse userResponse = createUserResponse(user);
        userEventSave(userEventLog);
        return userResponse;
    }

    public void userEventSave(UserEventLog userEventLog) {
        userEventLog.setProcessEndTime(LocalDateTime.now());
        userEventLog.setProcessTime((double)Duration.between(userEventLog.getProcessStartTime(), userEventLog.getProcessEndTime()).toNanos() / MILLION);
        userDao.save(userEventLog)
                .doOnError(error -> log.error(error))
                .subscribe();
    }

    private UserResponse createUserResponse(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

    private User createUser(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .createdAt(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .build();
    }

}
