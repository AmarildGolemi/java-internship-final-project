package com.lhind.application.utility.model.userdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "User Request")
public class UserRequestDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "First name of the user", example = "John", required = true)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Last name of the user", example = "Doe", required = true)
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Username of the user account", example = "john", required = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters")
    @ApiModelProperty(notes = "Password of the user account", example = "1234", required = true)
    private String password;

    @NotBlank(message = "Role is mandatory")
    @ApiModelProperty(notes = "Role of the user", example = "ROLE_ADMIN", required = true)
    private String role;

}
