package org.data.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Data
public class UserRequest {
    protected String username;
    protected String email;
    protected String firstName;
    protected String lastName;

    public UserRequest() {}
}
