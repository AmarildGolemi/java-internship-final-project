package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService underTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(userRepository);
    }

    @Test
    void shouldLoadUserByUsername() {
        //given
        String username = "john";

        User user = new User();
        user.setUsername(username);
        user.setPassword("1234");
        user.setRoles(new ArrayList<>());

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        org.springframework.security.core.userdetails.User expectedUser = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new HashSet<SimpleGrantedAuthority>()
        );

        //when
        UserDetails actualUser = underTest.loadUserByUsername(username);

        //then
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void willNotFindUserByUsername() {
        //given
        String username = "john";

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found in the database.");
    }

}