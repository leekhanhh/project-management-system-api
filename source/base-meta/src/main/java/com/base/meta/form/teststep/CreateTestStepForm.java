package com.base.meta.form.teststep;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTestStepForm {
    @ApiModelProperty(name = "step number", required = true)
    @NotNull(message = "stepNumber cannot be null")
    private Integer stepNumber;
    @ApiModelProperty(name = "teststep title", required = true)
    @NotNull(message = "action cannot be null")
    private String action;
    @ApiModelProperty(name = "teststep data", required = true)
    private String data;
    @ApiModelProperty(name = "teststep expected result", required = true)
    private String expectedResult;
    @ApiModelProperty(name = "testcase id", required = true)
    @NotNull(message = "testCaseId cannot be null")
    private Long testCaseId;
}
