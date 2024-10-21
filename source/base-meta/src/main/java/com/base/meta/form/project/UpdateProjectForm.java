package com.base.meta.form.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateProjectForm {
    @ApiModelProperty(value = "Project id", required = true)
    @NotNull(message = "Project id is required")
    private Long id;
    @ApiModelProperty(value = "Project name", required = true)
    @NotEmpty(message = "Project name is required")
    private String name;
    @ApiModelProperty(value = "Project description", required = true)
    private String description;
    @ApiModelProperty(value = "Project start date", required = true)
    private Date startDate;
    @ApiModelProperty(value = "Project end date", required = true)
    private Date endDate;
    @ApiModelProperty(value = "Project status", required = true, notes = "1: not started, 2: in progress, 3: completed, 4: cancelled")
    @NotNull(message = "Project status is required")
    private Long statusId;
}
