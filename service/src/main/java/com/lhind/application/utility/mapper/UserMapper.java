package com.lhind.application.utility.mapper;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserRequestDto;
import com.lhind.application.utility.model.userdto.UserResponseDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserResponseDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setUsername(user.getUsername());

        return userResponseDto;
    }

    public User userDtoToUser(UserRequestDto userRequestDto) {
        if (userRequestDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setUsername(userRequestDto.getUsername());

        return user;
    }

    public User userDtoToUser(UserPatchDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());

        return user;
    }

    public List<UserResponseDto> userToUserDto(List<User> users) {
        return users.stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

}
