package com.lhind.application.controller.v1;

import com.lhind.application.entity.User;
import com.lhind.application.service.UserService;
import com.lhind.application.utility.mapper.UserMapper;
import com.lhind.application.utility.model.userdto.UserDto;
import com.lhind.application.utility.model.userdto.UserPatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll()
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Min(1) Long id) {
        User existingUser = userService.findById(id);

        return new ResponseEntity<>(UserMapper.userToUserDto(existingUser), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto) {
        User userToSave = UserMapper.userDtoToUser(userDto);
        User savedUser = userService.save(userToSave);

        return new ResponseEntity<>(UserMapper.userToUserDto(savedUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable @Min(1) Long id,
                                          @Valid @RequestBody UserDto userDto) {
        User userToUpdate = UserMapper.userDtoToUser(userDto);
        User updatedUser = userService.update(id, userToUpdate);

        return new ResponseEntity<>(UserMapper.userToUserDto(updatedUser), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patch(@PathVariable @Min(1) Long id,
                                         @Valid @RequestBody UserPatchDto userDto) {
        User userToPatch = UserMapper.userDtoToUser(userDto);
        User patchedUser = userService.patch(id, userToPatch);

        return new ResponseEntity<>(UserMapper.userToUserDto(patchedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

}
