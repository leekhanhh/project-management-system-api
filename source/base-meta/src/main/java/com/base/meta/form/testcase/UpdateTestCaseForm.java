package com.base.meta.form.testcase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTestCaseForm {
    @ApiModelProperty(value = "testcase id", required = true)
    @NotNull(message = "testcase id is required")
    private Long id;
    @ApiModelProperty(value = "testcase name", required = true)
    @NotEmpty(message = "testcase name is required")
    private String name;
    @ApiModelProperty(value = "testcase precordition", required = true)
    private String precordition;
    @ApiModelProperty(value = "testcase menu path", required = true)
    private String menuPath;
}
