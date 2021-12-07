package com.lhind.application.utility.validation.datevalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class PatchFutureDateValidation implements ConstraintValidator<PatchFutureDate, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null){
            return true;
        }

        Period p = Period.between(LocalDate.now(), date.toLocalDate());

        return p.getDays() >= 0;
    }

}
