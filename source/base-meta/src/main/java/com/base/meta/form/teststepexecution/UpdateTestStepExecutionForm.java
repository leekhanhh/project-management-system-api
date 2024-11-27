package com.base.meta.form.teststepexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTestStepExecutionForm {
    @NotNull(message = "Id is required")
    @ApiModelProperty(value = "Test step execution id", required = true)
    private Long id;
    @ApiModelProperty(value = "Test step execution status id", required = true)
    @NotNull(message = "Test step execution status id is required")
    private Long categoryId;
}
