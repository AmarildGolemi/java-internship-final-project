package com.lhind.application.utility.model.userdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "User Response")
public class UserResponseDto {

    @ApiModelProperty(notes = "First name of the user", example = "John")
    private String firstName;

    @ApiModelProperty(notes = "Last name of the user", example = "Doe")
    private String lastName;

    @ApiModelProperty(notes = "Username of the user account", example = "john")
    private String username;

}
