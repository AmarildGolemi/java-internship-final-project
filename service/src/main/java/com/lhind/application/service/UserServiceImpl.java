package com.lhind.application.service;

import com.lhind.application.entity.Role;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.RoleRepository;
import com.lhind.application.repository.UserRepository;
import com.lhind.application.utility.mapper.UserMapper;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserRequestDto;
import com.lhind.application.utility.model.userdto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> findAll() {
        log.info("Finding all users.");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("No users retrieved from the database.");
        }

        log.info("Returning the list of users.");

        return UserMapper.userToUserDto(users);
    }

    @Override
    public UserResponseDto findById(Long id) {
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
    public UserResponseDto findByUsername(String username) {
        log.info("Finding user by username.");

        User foundUser = getUserByUsername(username);

        log.info("Returning found user.");

        return UserMapper.userToUserDto(foundUser);
    }

    @Override
    public User getByUsername(String username) {
        log.info("Finding user by username in the database.");

        User foundUser = getUserByUsername(username);

        log.info("Returning user.");

        return foundUser;
    }

    @Override
    @Transactional
    public UserResponseDto save(UserRequestDto userDto) {
        log.info("Saving user: {}.", userDto);

        User userToSave = UserMapper.userDtoToUser(userDto);
        userToSave.setPassword(passwordEncoder.encode(userDto.getPassword()));

        checkUserAlreadyExists(userToSave);

        List<Role> rolesToSet = getRolesToSet(userDto);
        userToSave.setRoles(rolesToSet);

        User savedUser = userRepository.save(userToSave);

        log.info("Returning saved user.");

        return UserMapper.userToUserDto(savedUser);
    }

    private List<Role> getRolesToSet(UserRequestDto userDto) {
        return userDto.getRoles().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toList());
    }

    private void checkUserAlreadyExists(User user) {
        log.info("Checking if user already exists.");

        Optional<User> existingUser = userRepository.findAllByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            log.error("User already exists.");

            throw new BadRequestException("User already exists.");
        }
    }

    @Override
    @Transactional
    public UserResponseDto patch(String username, UserPatchDto userDto) {
        log.info("Patching user with username: {}.", username);

        User userToPatch = getUserByUsername(username);

        patchUser(userDto, userToPatch);

        User patchedUser = userRepository.save(userToPatch);

        log.info("Returning patched user.");

        return UserMapper.userToUserDto(patchedUser);
    }

    private void patchUser(UserPatchDto userDto, User userToPatch) {
        if (userDto.getFirstName() != null) {
            userToPatch.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            userToPatch.setLastName(userDto.getLastName());
        }

        if (userDto.getPassword() != null) {
            userToPatch.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
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
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            log.error("User not found in the database.");

            throw new ResourceNotFoundException();
        }

        return userOptional.get();
    }

}
