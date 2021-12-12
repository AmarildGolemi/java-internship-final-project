package com.lhind.application.utility.model.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters")
    private String password;

    @NotNull
    private String role;

}
