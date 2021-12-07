package com.lhind.application.utility.validation.flightvalidation;

import com.lhind.application.utility.model.flightdto.FlightPatchDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.sql.Date;
import java.time.Period;

public class FlightPatchDateValidation implements ConstraintValidator<FlightPatchAvailableDates, FlightPatchDto> {

    @Override
    public boolean isValid(FlightPatchDto flightDto, ConstraintValidatorContext constraintValidatorContext) {
        Date departureDate = flightDto.getDepartureDate();
        Date arrivalDate = flightDto.getArrivalDate();

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
