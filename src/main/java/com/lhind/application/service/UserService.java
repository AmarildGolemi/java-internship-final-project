package com.lhind.application.service;

import com.lhind.application.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    @Transactional
    User save(User user);

    @Transactional
    User patch(String username, User user);

    @Transactional
    void saveUserAfterAddingNewTrip(User user);

    @Transactional
    String delete(String username);
}
