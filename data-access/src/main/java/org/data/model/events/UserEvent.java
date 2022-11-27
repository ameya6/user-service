package org.data.model.events;

public enum UserEvent {
    CREATE("USER_CREATE"),
    ACCESS("USER_ACCESS"),
    DELETE("USER_DELETE");

    UserEvent(String event){}
}
