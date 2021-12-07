package com.lhind.application.utility.validation;

import com.lhind.application.utility.model.TripDto.TripDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Period;

public class TripDateValidation implements ConstraintValidator<TripAvailableDates, TripDto> {

    @Override
    public boolean isValid(TripDto tripDto, ConstraintValidatorContext constraintValidatorContext) {
        Period p = Period.between(tripDto.getDepartureDate().toLocalDate(), tripDto.getArrivalDate().toLocalDate());

        return p.getDays() >= 0;
    }

}
