package org.data.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@SuperBuilder
@Data
public class UserAPIResponse extends UserResponse{
    private UUID id;

    public UserAPIResponse() {}
}
