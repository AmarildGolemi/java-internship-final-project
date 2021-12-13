package com.lhind.application.utility.validation.tripvalidation;

import com.lhind.application.utility.model.TripReason;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.lhind.application.utility.model.TripReason.*;

public class ReasonValidation implements ConstraintValidator<Reason, TripReason> {

    List<TripReason> reasons = Arrays.asList(EVENT, MEETING, TRAINING, PROJECT, WORKSHOP, OTHER);

    @Override
    public boolean isValid(TripReason tripReason, ConstraintValidatorContext constraintValidatorContext) {
        if (tripReason == null) {
            return true;
        }

        return reasons.contains(tripReason);
    }

    @Override
    public void initialize(Reason constraintAnnotation) {
    }
}
