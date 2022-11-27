package org.data.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@Table("user_event_log")
public class UserEventLog {

    private UUID id;
    private Long userId;
    private UUID userUUID;
    private LocalDateTime createdAt;
    private LocalDateTime processStartTime;
    private LocalDateTime processEndTime;
    private Double processTime;
    private UserEvent userEvent;
    private String exceptionMessage;

    public UserEventLog() {}
}
