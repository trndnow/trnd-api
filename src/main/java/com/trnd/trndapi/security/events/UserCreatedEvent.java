package com.trnd.trndapi.security.events;

import com.trnd.trndapi.security.dto.UserDto;
import com.trnd.trndapi.security.entity.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {

    private User user;

    public UserCreatedEvent(User user) {
        super(user);
        this.user = user;
    }
}
