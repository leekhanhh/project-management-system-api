package com.base.meta.form.testexecutionturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateTestExecutionTurnForm {
    @NotNull(message = "Id is required")
    @ApiModelProperty(value = "Test execution turn id", required = true)
    private Long id;
    @ApiModelProperty(value = "Test execution turn number", required = true)
    @NotNull(message = "Turn number is required")
    private Integer turnNumber;
    @ApiModelProperty(value = "Test execution turn plan start date", required = true)
    @NotNull(message = "Plan start date is required")
    private Date planStartDate;
    @ApiModelProperty(value = "Test execution turn plan end date", required = true)
    @NotNull(message = "Plan end date is required")
    private Date planEndDate;
    @ApiModelProperty(value = "Test execution turn actual start date")
    private Date actualStartDate;
    @ApiModelProperty(value = "Test execution turn actual end date")
    private Date actualEndDate;
    @ApiModelProperty(value = "Test execution turn assigned developer id", required = true)
    private Long assignedDeveloperId;
}
