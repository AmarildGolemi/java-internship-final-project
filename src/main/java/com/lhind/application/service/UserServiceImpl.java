package com.lhind.application.service;

import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.UserRepository;
import com.lhind.application.utility.mapper.UserMapper;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserPostDto;
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
    public List<UserDto> findAll() {
        log.info("Finding all users.");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("Zero users retrieved from the database.");
        }

        log.info("Returning the list of users.");

        return UserMapper.userToUserDto(users);
    }

    @Override
    public UserDto findById(Long id) {
        log.info("Finding user with id: {}", id);

        User foundUser = getUserById(id);

        log.info("Returning user.");

        return UserMapper.userToUserDto(foundUser);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id: {} not found", id);

                    throw new ResourceNotFoundException();
                });
    }

    @Override
    public UserDto findByUsername(String username) {
        log.info("Finding user by username.");

        User foundUser = getUserByUsername(username);

        log.info("Returning user.");

        return UserMapper.userToUserDto(foundUser);
    }

    @Override
    public User getByUsername(String username) {
        log.info("Finding user by username.");

        User foundUser = getUserByUsername(username);

        log.info("Returning user.");

        return foundUser;
    }

    @Override
    @Transactional
    public UserDto save(UserPostDto user) {
        log.info("Saving user: {}.", user);

        User userToSave = UserMapper.userDtoToUser(user);

        checkUserAlreadyExists(userToSave);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(userToSave);

        return UserMapper.userToUserDto(savedUser);
    }

    private void checkUserAlreadyExists(User user) {
        log.info("Checking is user already exists.");

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            log.error("User already exists.");

            throw new BadRequestException("User already exists.");
        }
    }

    @Override
    @Transactional
    public UserDto patch(String username, UserPatchDto user) {
        log.info("Patching user with username: {}.", username);

        User userToPatch = getUserByUsername(username);

        patchUser(user, userToPatch);

        log.info("Saving patched user.");

        User patchedUser = userRepository.save(userToPatch);

        return UserMapper.userToUserDto(patchedUser);
    }

    private void patchUser(UserPatchDto user, User userToPatch) {
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
    public void saveUserAfterAddingNewTrip(User user) {
        log.info("Saving user after adding new trip.");

        userRepository.save(user);
    }

    @Override
    @Transactional
    public String delete(String username) {
        log.info("Deleting user with username: {}", username);

        User userToDelete = getUserByUsername(username);

        userRepository.delete(userToDelete);

        return "User deleted";
    }

    private User getUserByUsername(String username) {
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
