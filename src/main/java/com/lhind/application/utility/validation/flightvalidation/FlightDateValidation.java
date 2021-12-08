package com.lhind.application.utility.validation.flightvalidation;

import com.lhind.application.utility.model.flightdto.FlightCreateDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.time.Period;

public class FlightDateValidation implements ConstraintValidator<FlightAvailableDates, FlightCreateDto> {

    @Override
    public boolean isValid(FlightCreateDto flightDto, ConstraintValidatorContext constraintValidatorContext) {
        if (flightDto.getDepartureDate() != null && flightDto.getArrivalDate() != null) {
            Period p = Period.between(flightDto.getDepartureDate().toLocalDate(), flightDto.getArrivalDate().toLocalDate());

            return p.getDays() >= 0;
        }

        throw new ValidationException();
    }

}
