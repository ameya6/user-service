package org.user.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user.service.UserService;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok(userService.create(userRequest));
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserResponse.builder().message("Failed creating user").build());
        }
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> get(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.get(id));
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserResponse.builder().message("Failed creating user").build());
        }
    }
}
