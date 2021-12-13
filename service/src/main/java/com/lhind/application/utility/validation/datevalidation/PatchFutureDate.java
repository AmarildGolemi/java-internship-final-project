package com.lhind.application.utility.validation.datevalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatchFutureDateValidation.class)
public @interface PatchFutureDate {

    String message() default "Date must be in the future.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
