package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.UserService;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("Accessing endpoint {} to find all users.", BASE_URL);

        authenticatedUserService.getLoggedUsername();

        List<UserDto> users = userService.findAll();

        log.info("Returning list of users.");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        log.info("Accessing endpoint {}/{} to find user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        UserDto existingUser = userService.findByUsername(username);

        log.info("Returning user.");

        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserPostDto userDto) {
        log.info("Accessing endpoint {} to post new user: {}.", BASE_URL, userDto);

        authenticatedUserService.getLoggedUsername();

        UserDto savedUser = userService.save(userDto);

        log.info("Returning new added user.");

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> patch(@PathVariable String username,
                                         @Valid @RequestBody UserPatchDto userDto) {
        log.info("Accessing endpoint {}/{} to patch user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        UserDto patchedUser = userService.patch(username, userDto);

        log.info("Returning patched user.");

        return new ResponseEntity<>(patchedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String username) {
        log.info("Accessing endpoint {}/{} to delete user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        log.info("Returning confirmation message.");

        return new ResponseEntity<>(userService.delete(username), HttpStatus.OK);
    }

}
