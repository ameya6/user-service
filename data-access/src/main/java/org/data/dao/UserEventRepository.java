package org.data.dao;

import org.data.model.events.UserEventLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserEventRepository extends ReactiveCrudRepository<UserEventLog, Integer> {
}
