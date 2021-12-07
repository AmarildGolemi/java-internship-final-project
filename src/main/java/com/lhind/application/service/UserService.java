package com.lhind.application.service;

import com.lhind.application.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    @Transactional
    User save(User user);

    @Transactional
    User update(Long id, User user);

    @Transactional
    User patch(Long id, User user);

    @Transactional
    void saveUserAfterAddingNewTrip(User user);

    @Transactional
    String delete(Long id);

}
