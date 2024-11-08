package com.base.meta.validation;

import com.base.meta.validation.impl.UserKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserKindValidation.class)
@Documented
public @interface UserKind {
    boolean allowNull() default false;

    String message() default "User kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
