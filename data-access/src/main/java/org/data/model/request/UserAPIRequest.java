package org.data.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@SuperBuilder
@Data
public class UserAPIRequest extends UserRequest {
    private UUID id;

    public UserAPIRequest() {}
}
