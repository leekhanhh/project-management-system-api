package com.base.meta.form.testcaseexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTestCaseExecutionForm {
    @ApiModelProperty(value = "Test execution turn id", required = true)
    @NotNull(message = "Test execution turn id is required")
    private Long testExecutionTurnId;
    @ApiModelProperty(value = "Test case id", required = true)
    private Long testCaseId;
    @ApiModelProperty(value = "Test suite id", required = true)
    private Long testSuiteId;
    @ApiModelProperty(value = "Test execution status id", required = true)
    @NotNull(message = "Test execution status id is required")
    private Long statusId;
    @ApiModelProperty(value = "Test execution type code id", required = true)
    @NotNull(message = "Test execution type code id is required")
    private Long testExecutionTypeCodeId;
}
