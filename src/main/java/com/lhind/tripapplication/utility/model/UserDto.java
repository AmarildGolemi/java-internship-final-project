package com.lhind.tripapplication.utility.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 20)
    private String lastName;

}
