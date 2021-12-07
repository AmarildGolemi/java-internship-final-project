package com.lhind.application.utility.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class FutureDateValidation implements ConstraintValidator<FutureDate, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Period p = Period.between(LocalDate.now(), date.toLocalDate());

        return p.getDays() >= 0;
    }

}
