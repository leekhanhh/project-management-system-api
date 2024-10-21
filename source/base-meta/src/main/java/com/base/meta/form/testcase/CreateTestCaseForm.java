package com.base.meta.form.testcase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTestCaseForm {
    @ApiModelProperty(value = "testcase name", required = true)
    @NotEmpty(message = "testcase name is required")
    private String name;
    @ApiModelProperty(value = "testcase precordition", required = true)
    private String precordition;
    @ApiModelProperty(value = "testcase menu path", required = true)
    private String menuPath;
    @ApiModelProperty(value = "program id", required = true)
    @NotNull(message = "program id is required")
    private Long programId;
}
