package com.base.meta.form.testdefect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTestDefectForm {
    @ApiModelProperty(value = "Test step execution id", required = true)
    @NotNull(message = "Test step execution id is required")
    private Long testStepExecutionId;
    @ApiModelProperty(value = "Test defect name", required = true)
    @NotEmpty(message = "Test defect name is required")
    private String name;
    @ApiModelProperty(value = "Test defect priority", required = true)
    @NotEmpty(message = "Test defect priority is required")
    private String priority;
    @ApiModelProperty(value = "Test defect severity", required = true)
    @NotEmpty(message = "Test defect severity is required")
    private String severity;
    @ApiModelProperty(value = "Test defect status id", required = true)
    @NotNull(message = "Test defect status id is required")
    private Long statusId;
    @ApiModelProperty(value = "Assigned developer id", required = true)
    @NotNull(message = "Assigned developer id is required")
    private Long assignedDeveloperId;
    @ApiModelProperty(value = "Test defect description")
    private String description;
    @ApiModelProperty(value = "Check whether send email notification or not")
    private Boolean sendEmailNotification;
}
