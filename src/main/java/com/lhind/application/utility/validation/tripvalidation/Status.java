package com.lhind.application.utility.validation.tripvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidation.class)
public @interface Status {

    String message() default "Status must be a valid status.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
