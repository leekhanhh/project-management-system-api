package com.base.meta.form.testsuiteexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTestSuiteExecutionForm {
    @ApiModelProperty(value = "Test suite execution order number", required = true)
    private Integer orderNumber;
    @ApiModelProperty(value = "Test suite execution status id", required = true)
    private Long statusId;
    @NotNull(message = "Test suite id is required")
    @ApiModelProperty(value = "Test suite id", required = true)
    private Long testSuiteId;
    @NotNull(message = "Test execution turn id is required")
    @ApiModelProperty(value = "Test execution turn id", required = true)
    private Long testExecutionTurnId;
}
