package com.lhind.application.utility.model.userdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "User Patch")
public class UserPatchDto {

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "First name of the user", example = "John")
    private String firstName;

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Last name of the user", example = "Doe")
    private String lastName;

    @Size(min = 4, max = 20)
    @ApiModelProperty(notes = "Password of the user account", example = "1234")
    private String password;

}
