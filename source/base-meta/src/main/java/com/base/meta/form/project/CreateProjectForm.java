package com.base.meta.form.project;

import com.base.meta.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateProjectForm {
    @ApiModelProperty(value = "Project name", required = true)
    @NotEmpty(message = "Project name is required")
    private String name;
    @ApiModelProperty(value = "Project description", required = true)
    @NotEmpty(message = "Project description is required")
    private String description;
    @ApiModelProperty(value = "Project start date", required = true)
    @FutureOrPresent(message = "Project start date must be in the present or future")
    private Date startDate;
    @ApiModelProperty(value = "Project end date", required = true)
    @FutureOrPresent(message = "Project end date must be in the present or future")
    private Date endDate;
    @NotNull(message = "Project status is required")
    @ApiModelProperty(value = "Project status", required = true, notes = "1: not started, 2: in progress, 3: completed, 4: cancelled")
    private Long statusId;
}
