package com.base.meta.form.testexecution;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateTestExecutionForm {
    @ApiModelProperty(value = "test execution name", required = true)
    @NotEmpty(message = "test execution name is required")
    private String name;
    @ApiModelProperty(value = "category id", required = true)
    @NotNull(message = "category id is required")
    private Long categoryId;
    @ApiModelProperty(value = "status id", required = true)
    private Long statusId;
    @ApiModelProperty(value = "assigned developer id", required = true)
    private Long assignedDeveloperId;
    @ApiModelProperty(value = "project id", required = true)
    @NotNull(message = "program id is required")
    private Long programId;
    @ApiModelProperty(value = "detail", required = true)
    private String detail;
    @ApiModelProperty(value = "plan start date", required = true)
    @NotNull(message = "plan start date is required")
    private Date planStartDate;
    @ApiModelProperty(value = "plan end date", required = true)
    @NotNull(message = "plan end date is required")
    private Date planEndDate;
}
