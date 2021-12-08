package com.lhind.application.utility.mapper;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserPostDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());

        return userDto;
    }

    public User userDtoToUser(UserPostDto userPostDto) {
        if (userPostDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(userPostDto.getFirstName());
        user.setLastName(userPostDto.getLastName());
        user.setUsername(userPostDto.getUsername());
        user.setPassword(userPostDto.getPassword());

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

    public List<UserDto> userToUserDto(List<User> users) {
        return users.stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

}
