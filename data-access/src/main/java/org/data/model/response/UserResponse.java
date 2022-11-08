package org.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@SuperBuilder
public class UserResponse {
    protected String username;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String message;

    public UserResponse() {}
}
