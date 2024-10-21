package com.base.meta.form.testcaseexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTestCaseExecutionForm {
    @NotNull(message = "Id is required")
    @ApiModelProperty(value = "Test case execution id", required = true)
    private Long id;
    @ApiModelProperty(value = "Test execution status id", required = true)
    @NotNull(message = "Test execution status id is required")
    private Long statusId;
    @ApiModelProperty(value = "Test execution type code id", required = true)
    @NotNull(message = "Test execution type code id is required")
    private Long testExecutionTypeCodeId;
}
