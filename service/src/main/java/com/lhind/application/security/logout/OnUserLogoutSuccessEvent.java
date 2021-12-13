package com.lhind.application.security.logout;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private final String username;
    private final String token;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String username, String token) {
        super(username);
        this.username = username;
        this.token = token;
        this.eventTime = Date.from(Instant.now());
    }

}
