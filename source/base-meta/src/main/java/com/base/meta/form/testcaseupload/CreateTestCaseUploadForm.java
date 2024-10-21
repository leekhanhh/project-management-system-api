package com.base.meta.form.testcaseupload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTestCaseUploadForm {
    @ApiModelProperty(name = "test case precondition", required = true)
    private String testCasePrecondition;
    @ApiModelProperty(name = "test case name", required = true)
    @NotEmpty(message = "test case name cannot be null")
    private String testCaseName;
    @ApiModelProperty(name = "test case menu path", required = true)
    private String testCaseMenuPath;
    @ApiModelProperty(name = "test steps action", required = true)
    private String testStepsAction;
    @ApiModelProperty(name = "test steps data", required = true)
    @NotEmpty(message = "test steps data cannot be null")
    private String testStepsData;
    @ApiModelProperty(name = "test steps expected result", required = true)
    private String testStepsExpectedResult;
    @ApiModelProperty(name = "test steps actual result", required = true)
    private String testStepsActualResult;
    @ApiModelProperty(name = "project id", required = true)
    @NotNull(message = "project id cannot be null")
    private Long projectId;
    @ApiModelProperty(name = "program id", required = true)
    private Long programId;
}
