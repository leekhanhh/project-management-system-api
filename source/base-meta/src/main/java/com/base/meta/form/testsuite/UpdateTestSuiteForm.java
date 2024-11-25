package com.base.meta.form.testsuite;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class UpdateTestSuiteForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null")
    private Long id;
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name cannot be null")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "account id")
    private Long accountId;
}
