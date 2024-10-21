package com.base.meta.form.teststep;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTestStepForm {
    @ApiModelProperty(name = "teststep id", required = true)
    @NotNull(message = "id cannot be null")
    private Long id;
    @ApiModelProperty(name = "teststep title", required = true)
    @NotNull(message = "action cannot be null")
    private String action;
    @ApiModelProperty(name = "teststep data", required = true)
    private String data;
    @ApiModelProperty(name = "teststep expected result", required = true)
    private String expectedResult;
}
