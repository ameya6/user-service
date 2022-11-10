package org.data.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class UserClientRequest extends UserRequest implements Serializable {
    @Override
    public String toString() {
        return "UserClientRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
