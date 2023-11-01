package com.trnd.trndapi.security.events;

import com.trnd.trndapi.security.dto.UserDto;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {

    private UserDto userDto;

    public UserCreatedEvent(UserDto userDto) {
        super(userDto);
        this.userDto = userDto;
    }
}
