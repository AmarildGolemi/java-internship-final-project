package com.lhind.application.utility.validation.tripvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TripDateValidation.class)
public @interface TripAvailableDates {

    String message() default "Departure date must be before arrival date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
