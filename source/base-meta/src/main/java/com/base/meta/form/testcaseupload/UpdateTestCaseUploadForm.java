package com.base.meta.form.testcaseupload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTestCaseUploadForm {
    @ApiModelProperty(name = "test case id", required = true)
    @NotNull(message = "test case id cannot be null")
    private Long id;
    @ApiModelProperty(name = "test case precondition", required = true)
    private String testCasePrecondition;
    @ApiModelProperty(name = "test case name", required = true)
    @NotEmpty(message = "test case name cannot be null")
    private String testCaseName;
    @ApiModelProperty(name = "test case menu path", required = true)
    private String testCaseMenuPath;
    @ApiModelProperty(name = "test steps action", required = true)
    private String testStepAction;
    @ApiModelProperty(name = "test steps data", required = true)
    @NotEmpty(message = "test steps data cannot be null")
    private String testStepData;
    @ApiModelProperty(name = "test steps expected result", required = true)
    private String testStepExpectedResult;
    @ApiModelProperty(name = "program id", required = true)
    private Long programId;
}
