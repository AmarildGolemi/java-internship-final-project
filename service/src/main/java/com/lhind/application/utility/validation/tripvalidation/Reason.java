package com.lhind.application.utility.validation.tripvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReasonValidation.class)
public @interface Reason {

    String message() default "Trip reason must be a valid reason.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
