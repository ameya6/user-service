package org.user.service;

import lombok.extern.log4j.Log4j2;
import org.data.model.entity.User;
import org.data.model.request.UserClientRequest;
import org.data.model.response.UserClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.UserDao;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class UserClientService {

    @Autowired
    private UserDao userDao;

    public UserClientResponse create(UserClientRequest userRequest) {
        UserClientResponse response = null;
        User user = createUser(userRequest);
        try {
            userDao.save(user);
            response = createUserResponse(user);
            //log.info(user + " " + response);
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            log.error(user + " " + userRequest);
        } finally {
            return response;
        }
    }

    private UserClientResponse createUserResponse(User user){
        return UserClientResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .message("User " + user.getFirstName() + " " + user.getLastName() + " created successfully")
                .build();
    }

    private User createUser(UserClientRequest request) {
        return User.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
