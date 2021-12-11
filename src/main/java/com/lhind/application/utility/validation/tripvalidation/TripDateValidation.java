package com.lhind.application.utility.validation.tripvalidation;

import com.lhind.application.utility.model.tripdto.TripPostDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.time.Period;

public class TripDateValidation implements ConstraintValidator<TripAvailableDates, TripPostDto> {

    @Override
    public boolean isValid(TripPostDto tripDto, ConstraintValidatorContext constraintValidatorContext) {
        if(tripDto.getDepartureDate() != null && tripDto.getArrivalDate() != null){
            Period p = Period.between(tripDto.getDepartureDate().toLocalDate(), tripDto.getArrivalDate().toLocalDate());

            return p.getDays() >= 0;
        }

        throw new ValidationException();
    }

}
