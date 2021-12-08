package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        log.info("Finding all users.");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("Zero users retrieved from the database.");
        }

        log.info("Returning the list of users.");

        return users;
    }

    @Override
    public User findById(Long id) {
        log.info("Finding user with id: {}", id);

        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id: {} not found", id);

                    throw new ResourceNotFoundException();
                });

        log.info("Returning user.");

        return foundUser;
    }

    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username.");

        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User with username: {} not found", username);

                    throw new ResourceNotFoundException();
                });

        log.info("Returning user.");

        return foundUser;
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Saving user: {}.", user);

        checkUserAlreadyExists(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void checkUserAlreadyExists(User user) {
        log.info("Checking is user already exists.");

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if(existingUser.isPresent()){
            log.error("User already exists.");

            throw new BadRequestException("User already exists.");
        }
    }

    @Override
    @Transactional
    public User patch(String username, User user) {
        log.info("Patching user with username: {}.", username);

        validateUser(user);

        User userToPatch = getUser(username);

        patchUser(user, userToPatch);

        log.info("Saving patched user.");

        return userRepository.save(userToPatch);
    }

    @Override
    @Transactional
    public void saveUserAfterAddingNewTrip(User user){
        log.info("Saving user after adding new trip.");

        userRepository.save(user);
    }

    private void validateUser(User user) {
        log.info("Checking user id is not provided.");

        if (user.getId() != null) {
            log.error("User id:{} is provided", user.getId());

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

        if (user.getPassword() != null) {
            userToPatch.setPassword(user.getPassword());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    @Transactional
    public String delete(String username) {
        log.info("Deleting user with username: {}", username);

        User userToDelete = getUser(username);

        userRepository.delete(userToDelete);

        return "User deleted";
    }

    private User getUser(String username) {
        log.info("Finding user in the database.");

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            log.error("User not found in the database.");

            throw new ResourceNotFoundException();
        }

        log.info("Retrieving user.");

        return userOptional.get();
    }

}
