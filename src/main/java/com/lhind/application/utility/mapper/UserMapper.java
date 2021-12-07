package com.lhind.application.utility.mapper;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    public User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }

    public User userDtoToUser(UserPatchDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }


}
