package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserPostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    User getByUsername(String username);

    @Transactional
    UserDto save(UserPostDto user);

    @Transactional
    UserDto patch(String username, UserPatchDto user);

    @Transactional
    void saveUserAfterAddingNewTrip(User user);

    @Transactional
    String delete(String username);

}
