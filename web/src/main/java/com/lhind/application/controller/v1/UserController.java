package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.UserService;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import com.lhind.application.utility.model.userdto.UserRequestDto;
import com.lhind.application.utility.model.userdto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lhind.application.controller.v1.UserController.BASE_URL;
import static com.lhind.application.swagger.SwaggerConstant.USER_API_TAG;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Api(tags = {USER_API_TAG})
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find all available users", response = UserResponseDto.class)
    public ResponseEntity<List<UserResponseDto>> findAll() {
        log.info("Accessing endpoint {} to find all users.", BASE_URL);

        authenticatedUserService.getLoggedUsername();

        List<UserResponseDto> users = userService.findAll();

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find existing flight by username", response = UserResponseDto.class)
    public ResponseEntity<UserResponseDto> findByUsername(@PathVariable String username) {
        log.info("Accessing endpoint {}/{} to find user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        UserResponseDto existingUser = userService.findByUsername(username);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Add new user", response = UserResponseDto.class)
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userDto) {
        log.info("Accessing endpoint {} to post new user: {}.", BASE_URL, userDto);

        authenticatedUserService.getLoggedUsername();

        UserResponseDto savedUser = userService.save(userDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Patch an existing user", response = UserResponseDto.class)
    public ResponseEntity<UserResponseDto> patch(@PathVariable String username,
                                                 @Valid @RequestBody UserPatchDto userDto) {
        log.info("Accessing endpoint {}/{} to patch user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        UserResponseDto patchedUser = userService.patch(username, userDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(patchedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete an existing user", response = String.class)
    public ResponseEntity<String> delete(@PathVariable String username) {
        log.info("Accessing endpoint {}/{} to delete user by username.", BASE_URL, username);

        authenticatedUserService.getLoggedUsername();

        String deleteConfirmationMessage = userService.delete(username);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(deleteConfirmationMessage, HttpStatus.OK);
    }

}
