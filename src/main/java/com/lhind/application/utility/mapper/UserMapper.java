package com.lhind.application.utility.mapper;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto userToUserDto(User User) {
        if (User == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFirstName(User.getFirstName());
        userDto.setLastName(User.getLastName());

        return userDto;
    }

    public User userDtoToUser(UserDto UserDto) {
        if (UserDto == null) {
            return null;
        }

        User user = new User();

        user.setFirstName(UserDto.getFirstName());
        user.setLastName(UserDto.getLastName());

        return user;
    }

}
