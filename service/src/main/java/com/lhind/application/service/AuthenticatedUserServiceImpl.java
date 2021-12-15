package com.lhind.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    @Override
    public String getLoggedUsername() {
        log.info("Getting logged user's username.");

        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (loggedUsername == null) {
            log.error("User is not logged in.");

            throw new IllegalStateException();
        }

        log.info("Logged user with username '{}' is accessing the endpoint.", loggedUsername);

        return loggedUsername;
    }

}
