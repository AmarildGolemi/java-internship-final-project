package com.lhind.application.utility.model.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 20, message = "First name should be between 3 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 20, message = "Last name should be between 3 and 20 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters")
    private String password;

}
