package com.base.meta.validation.impl;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.validation.UserKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserKindValidation implements ConstraintValidator<UserKind, Integer> {
//    private boolean allowNull;
//    @Override
//    public void initialize(UserKind constraintAnnotation) {
//        allowNull = constraintAnnotation.allowNull();
//    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == BaseMetaConstant.USER_KIND_ADMIN
                || value == BaseMetaConstant.USER_KIND_DEV
                || value == BaseMetaConstant.USER_KIND_TESTER
                || value == BaseMetaConstant.USER_KIND_PM;
    }
}
