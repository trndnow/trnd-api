package com.trnd.trndapi.events;

import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.context.SecurityContext;

@Getter
public class UserCreatedEvent extends ApplicationEvent {

    private User user;
    private SignupRequest signupRequest;

    public UserCreatedEvent(User user) {
        super(user);
        this.user = user;
    }
    public UserCreatedEvent(User user, SignupRequest signupRequest) {
        super(user);
        this.user = user;
        this.signupRequest = signupRequest;
    }
}
