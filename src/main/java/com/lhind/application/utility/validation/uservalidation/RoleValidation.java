package com.lhind.application.utility.validation.uservalidation;

import com.lhind.application.utility.model.Role;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.lhind.application.utility.model.Role.ROLE_ADMIN;
import static com.lhind.application.utility.model.Role.ROLE_USER;

@RequiredArgsConstructor
public class RoleValidation implements ConstraintValidator<ValidRole, Role> {

    List<Role> roles = Arrays.asList(ROLE_ADMIN, ROLE_USER);

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext constraintValidatorContext) {
        return roles.contains(role);
    }

}
