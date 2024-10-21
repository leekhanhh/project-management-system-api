package com.base.meta.form.testsuite;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTestSuiteForm {
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name cannot be null")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "testPlanId", required = true)
    private Long testPlanId;
    @ApiModelProperty(name = "accountId", required = true)
    private Long accountId;
}
