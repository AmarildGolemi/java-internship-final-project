package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserRequestDto;
import com.lhind.application.utility.model.userdto.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto findById(Long id);

    UserResponseDto findByUsername(String username);

    User getByUsername(String username);

    @Transactional
    UserResponseDto save(UserRequestDto user);

    @Transactional
    UserResponseDto patch(String username, UserPatchDto user);

    @Transactional
    void saveUserAfterAddingNewTrip(User user);

    @Transactional
    String delete(String username);

}
