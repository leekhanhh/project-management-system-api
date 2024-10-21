package com.base.meta.validation;

import com.base.meta.validation.impl.CategoryTypeValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryTypeValidation.class)
@Documented
public @interface CategoryType{
    boolean allowNull() default false;

    String message() default "Category kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
