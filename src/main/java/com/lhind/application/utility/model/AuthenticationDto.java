package com.lhind.application.utility.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationDto {

    private String email;

    private String password;
}
