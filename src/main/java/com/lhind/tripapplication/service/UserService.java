package com.lhind.tripapplication.service;

import com.lhind.tripapplication.entity.Trip;
import com.lhind.tripapplication.entity.User;
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
    String delete(Long id);

    @Transactional
    Trip addTrip(Long id, Trip trip);

}
