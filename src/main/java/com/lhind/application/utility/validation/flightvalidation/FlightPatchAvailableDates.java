package com.lhind.application.utility.validation.flightvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlightPatchDateValidation.class)
public @interface FlightPatchAvailableDates {

    String message() default "Departure date must be before arrival date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
