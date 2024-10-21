package com.base.meta.validation.impl;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.validation.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class StatusValidation implements ConstraintValidator<Status, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Status constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        if (status == null && allowNull) {
            return true;
        }
        return Objects.equals(status, BaseMetaConstant.STATUS_VALIDATION_NOT_STARTED)
                || Objects.equals(status, BaseMetaConstant.STATUS_VALIDATION_IN_PROGRESS)
                || Objects.equals(status, BaseMetaConstant.STATUS_VALIDATION_COMPLETED)
                || Objects.equals(status, BaseMetaConstant.STATUS_VALIDATION_CANCELLED);
    }
}
