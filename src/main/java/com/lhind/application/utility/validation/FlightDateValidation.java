package com.lhind.application.utility.validation;

import com.lhind.application.utility.model.FlightDto.FlightDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Period;

public class FlightDateValidation implements ConstraintValidator<FlightAvailableDates, FlightDto> {

    @Override
    public boolean isValid(FlightDto flightDto, ConstraintValidatorContext constraintValidatorContext) {
        Period p = Period.between(flightDto.getDepartureDate().toLocalDate(), flightDto.getArrivalDate().toLocalDate());

        return p.getDays() >= 0;
    }

}
