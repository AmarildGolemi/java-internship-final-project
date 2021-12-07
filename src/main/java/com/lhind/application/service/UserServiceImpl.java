package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            //TODO: Log warning.
        }

        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser != null){
            throw new BadRequestException("User already exists.");
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        validateUser(user);

        Optional<User> userOptional = userRepository.findById(id);
        User userToUpdate = getUser(userOptional);

        user.setId(userToUpdate.getId());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User patch(Long id, User user) {
        validateUser(user);

        Optional<User> userOptional = userRepository.findById(id);
        User userToPatch = getUser(userOptional);

        patchUser(user, userToPatch);

        return userRepository.save(userToPatch);
    }

    private void validateUser(User user) {
        if (user.getId() != null) {
            throw new BadRequestException("Id should not be provided.");
        }
    }

    private void patchUser(User user, User userToPatch) {
        if (user.getFirstName() != null) {
            userToPatch.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            userToPatch.setLastName(user.getLastName());
        }

        if (user.getEmail() != null) {
            userToPatch.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            userToPatch.setPassword(user.getPassword());
        }
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User userToDelete = getUser(userOptional);

        userRepository.delete(userToDelete);

        return "User deleted";
    }

    private User getUser(Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return userOptional.get();
    }

}
