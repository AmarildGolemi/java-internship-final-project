package com.lhind.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AuthenticatedUserServiceImplTest {

    private AuthenticatedUserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticatedUserServiceImpl();
    }

    @Test
    void canGetLoggedUsername() {
        //given
        String loggedUsername = "john";

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return "john";
            }            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }


        };
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        String expectedUsername = underTest.getLoggedUsername();

        //then
        assertThat(expectedUsername).isEqualTo(loggedUsername);
    }

    @Test
    void willThrowWhenLoggedUserNotLoggedIn() {
        //given
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return null;
            }            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }


        };
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        //then
        assertThatThrownBy(() -> underTest.getLoggedUsername())
                .isInstanceOf(IllegalStateException.class);
    }

}