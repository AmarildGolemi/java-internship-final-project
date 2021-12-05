package com.lhind.application.utility.model.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserPatchDto {

    @Size(min = 3, max = 20)
    private String firstName;

    @Size(min = 3, max = 20)
    private String lastName;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(min = 4, max = 20)
    private String password;

}
