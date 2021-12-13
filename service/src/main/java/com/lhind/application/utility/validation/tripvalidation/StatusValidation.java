package com.lhind.application.utility.validation.tripvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.lhind.application.utility.model.Status.*;

public class StatusValidation implements ConstraintValidator<Status, com.lhind.application.utility.model.Status> {

    List<com.lhind.application.utility.model.Status> statuses = Arrays.asList(CREATED, WAITING_FOR_APPROVAL, APPROVED, REJECTED);

    @Override
    public boolean isValid(com.lhind.application.utility.model.Status status, ConstraintValidatorContext constraintValidatorContext) {
        if (status == null) {
            return true;
        }

        return statuses.contains(status);
    }

}
