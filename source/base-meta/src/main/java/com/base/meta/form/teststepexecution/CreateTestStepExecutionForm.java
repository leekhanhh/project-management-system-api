package com.base.meta.form.teststepexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTestStepExecutionForm {
    @NotNull(message = "Test step id is required")
    @ApiModelProperty(value = "Test step id", required = true)
    private Long testStepId;
    @NotNull(message = "Test case execution id is required")
    @ApiModelProperty(value = "Test case execution id", required = true)
    private Long testCaseExecutionId;
    @ApiModelProperty(value = "Test step execution status id", required = true)
    @NotNull(message = "Test step execution status id is required")
    private Long categoryId;
}
