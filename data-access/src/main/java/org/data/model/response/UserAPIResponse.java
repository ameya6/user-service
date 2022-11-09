package org.data.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Data
@AllArgsConstructor
public class UserAPIResponse extends UserResponse{
    private UUID id;

    public UserAPIResponse() {}
}
