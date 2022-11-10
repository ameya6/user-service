package org.data.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
public class UserClientResponse extends UserResponse implements Serializable {
    @Override
    public String toString() {
        return "UserClientResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
