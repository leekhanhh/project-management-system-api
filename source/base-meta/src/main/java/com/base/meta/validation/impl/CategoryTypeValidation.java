package com.base.meta.validation.impl;


import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.validation.CategoryType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CategoryTypeValidation implements ConstraintValidator<CategoryType, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(CategoryType constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer categoryKind, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryKind == null && allowNull) {
            return true;
        }
        return Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_ACCOUNT)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_PROJECT)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_REQUIREMENT)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_PROGRAM)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_TEST_EXECUTION)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_TEST_SUITE_EXECUTION)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_TEST_CASE_EXECUTION)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_TEST_STEP_EXECUTION)
                || Objects.equals(categoryKind, BaseMetaConstant.CATEGORY_KIND_TEST_DEFECT);
    }
}