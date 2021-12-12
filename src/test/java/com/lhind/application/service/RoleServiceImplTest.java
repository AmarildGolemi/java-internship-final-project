package com.lhind.application.service;

import com.lhind.application.entity.Role;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private RoleService underTest;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        underTest = new RoleServiceImpl(roleRepository);
    }

    @Test
    void save() {
        //given
        Role role = new Role();

        //when
        underTest.save(role);

        //then
        ArgumentCaptor<Role> roleArgumentCaptor =
                ArgumentCaptor.forClass(Role.class);

        verify(roleRepository).save(roleArgumentCaptor.capture());

        Role capturedRole = roleArgumentCaptor.getValue();

        assertThat(capturedRole).isEqualTo(role);
    }

    @Test
    void canFindByUsername() {
        //given
        String name = "admin";

        given(roleRepository.findByName(name)).willReturn(new Role());

        //when
        underTest.findByName(name);

        //then
        verify(roleRepository).findByName(name);
    }

    @Test
    void willThrowWhenUserNotFoundByUsername() {
        //given
        String name = "admin";
        given(roleRepository.findByName(name)).willReturn(null);

        //when
        //then
        assertThatThrownBy(() -> underTest.findByName(name))
                .isInstanceOf(ResourceNotFoundException.class);
    }


}