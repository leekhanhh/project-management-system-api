package com.base.meta.validation;

import com.base.meta.validation.impl.NumberFieldValidation;
import com.base.meta.validation.impl.StatusValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberFieldValidation.class)
@Documented
public @interface NumberField {
    boolean allowNull() default false;

    String message() default "Number field invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
