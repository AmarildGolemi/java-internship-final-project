package com.lhind.application.utility.validation.tripvalidation;

import com.lhind.application.utility.model.tripdto.TripPatchDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.sql.Date;
import java.time.Period;

public class TripPatchDateValidation implements ConstraintValidator<TripPatchAvailableDates, TripPatchDto> {

    @Override
    public boolean isValid(TripPatchDto tripDto, ConstraintValidatorContext constraintValidatorContext) {
        Date departureDate = tripDto.getDepartureDate();
        Date arrivalDate = tripDto.getArrivalDate();

        if (departureDate == null && arrivalDate == null) {
            return true;
        }

        if (departureDate != null && arrivalDate != null) {
            Period p = Period.between(departureDate.toLocalDate(), arrivalDate.toLocalDate());

            return p.getDays() >= 0;
        }


        throw new ValidationException();
    }

}
