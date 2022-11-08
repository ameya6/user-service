package org.user.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.request.UserClientRequest;
import org.data.model.response.UserClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.user.service.UserAPIService;
import org.user.service.UserClientService;

@RestController
@RequestMapping("/user/service")
@Log4j2
public class UserServiceController {
    @Autowired
    private UserAPIService userService;

    /*@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserClientResponse> create(@RequestBody UserClientRequest userRequest) {
        try {
            return ResponseEntity.ok(userService.create(userRequest));
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserClientResponse.builder().message("Failed creating user").build());
        }
    }*/
}
