package com.lhind.application.service;

import com.lhind.application.entity.Role;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.RoleRepository;
import com.lhind.application.repository.UserRepository;
import com.lhind.application.service.UserService;
import com.lhind.application.service.UserServiceImpl;
import com.lhind.application.utility.mapper.UserMapper;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService underTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void canFindAll() {
        //when
        underTest.findAll();

        //then
        verify(userRepository).findAll();
    }

    @Test
    void canFindUserById() {
        //given
        Long id = 1L;

        given(userRepository.findById(id)).willReturn(Optional.of(new User()));

        //when
        underTest.findById(id);

        //then
        verify(userRepository).findById(id);
    }

    @Test
    void willThrowWhenUserNotFoundById() {
        //given
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canFindUserByUsername() {
        //given
        String username = "john";

        given(userRepository.findByUsername(username)).willReturn(Optional.of(new User()));

        //when
        underTest.findByUsername(username);

        //then
        verify(userRepository).findByUsername(username);
    }

    @Test
    void willThrowWhenUserNotFoundByUsername() {
        //given
        String username = "john";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findByUsername(username))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canGetUserByUsername() {
        //given
        String username = "john";

        given(userRepository.findByUsername(username)).willReturn(Optional.of(new User()));

        //when
        underTest.getByUsername(username);

        //then
        verify(userRepository).findByUsername(username);
    }

    @Test
    void willThrowWhenCannotGetUserByUsername() {
        //given
        String username = "john";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.getByUsername(username))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canSaveUser() {
        //given
        Role role = new Role();
        role.setName("admin");

        UserRequestDto userDto = new UserRequestDto();
        userDto.setFirstName("john");
        userDto.setLastName("doe");
        userDto.setUsername("john");
        userDto.setPassword("1234");
        userDto.setRole("admin");

        User userToSave = UserMapper.userDtoToUser(userDto);
        userToSave.setRoles(List.of(role));

        given(userRepository.findAllByUsername(anyString())).willReturn(Optional.empty());
        given(roleRepository.findByName(userDto.getRole())).willReturn(role);

        //when
        underTest.save(userDto);

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(userToSave);
    }

    @Test
    void willThrowWhenSaveUsernameExists() {
        //given
        UserRequestDto userDto = new UserRequestDto();
        userDto.setFirstName("john");
        userDto.setLastName("doe");
        userDto.setUsername("john");
        userDto.setPassword("1234");

        User userToSave = UserMapper.userDtoToUser(userDto);

        given(userRepository.findAllByUsername(userToSave.getUsername())).willReturn(Optional.of(new User()));

        //when
        //then
        assertThatThrownBy(() -> underTest.save(userDto))
                .isInstanceOf(BadRequestException.class);

        verify(userRepository, never()).save(userToSave);
    }

    @Test
    void canPatchUserFieldsNotProvided() {
        //given
        String username = "john";
        UserPatchDto userDto = new UserPatchDto();
        User userToPatch = new User();

        given(userRepository.findByUsername(username)).willReturn(Optional.of(userToPatch));

        //when
        underTest.patch(username, userDto);

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(userToPatch);
    }

    @Test
    void canPatchUserFieldsProvided() {
        //given
        String username = "john";

        UserPatchDto userDto = new UserPatchDto();
        userDto.setFirstName("john");
        userDto.setLastName("doe");
        userDto.setPassword("1234");

        User userToPatch = new User();

        given(userRepository.findByUsername(username)).willReturn(Optional.of(userToPatch));

        //when
        underTest.patch(username, userDto);

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(userToPatch);
    }

    @Test
    void willNotPatchWhenUserNotFoundByUsername() {
        //given
        String username = "john";
        UserPatchDto userPatchDto = new UserPatchDto();
        User userToPatch = new User();

        given(userRepository.findByUsername(username))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(username, userPatchDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never()).save(userToPatch);
    }

    @Test
    void saveUserAfterAddingNewTrip() {
        //given
        User user = new User();

        //when
        underTest.saveUserAfterAddingNewTrip(user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    void canDeleteUser() {
        // given
        String username = "john";
        User user = new User();

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // when
        underTest.delete(username);

        // then
        verify(userRepository).delete(user);
    }

    @Test
    void willNotDeleteUserWhenRegistrationNotFound() {
        //given
        String username = "john";
        User user = new User();

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.delete(username))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never()).delete(user);
    }

}