package com.trnd.trndapi.events;

import com.trnd.trndapi.entity.Role;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@SpringBootTest
public class EventPublisherTest {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserEventListener userEventListener;

    @MockBean
    private ApplicationListener<UserCreatedEvent> eventListener;

    //@Test
    public void whenEventPublished_thenEventListenerInvoked() {
        // Create an instance of the event
        User user = User.builder()
                .id(2L)
                .email("event-test@dummy.com")
                .mobile("0000000000")
                .uniqueId(UUID.randomUUID())
                .userStatus(AccountStatus.ACTIVE)
                .role(createRolesSet(ERole.ROLE_MERCHANT))
                .emailVerifiedFlag(true)
                .build();
        UserCreatedEvent event = new UserCreatedEvent(user);

        // Publish the event
        eventPublisher.publishEvent(event);

        // Verify that the listener is invoked
//        Mockito.verify(eventListener, Mockito.timeout(100)).onApplicationEvent(event);
    }
    private Role createRolesSet(ERole role) {
        Role newRole = new Role();
        newRole.setName(role);
        return newRole;
    }
}
