package com.base.meta.form.program;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class UpdateProgramForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "program's id cannot be null")
    private Long id;
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "program's name cannot be null")
    private String name;
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @ApiModelProperty(name = "program type", required = true)
    @NotNull(message = "program type cannot be null")
    private Long programTypeId;
    @ApiModelProperty(name = "status", required = true)
    @NotNull(message = "status cannot be null")
    private Long statusId;
    @ApiModelProperty(name = "program category", required = true)
    private String programCategory;
    @ApiModelProperty(name = "start date", required = true)
    private Date startDate;
    @ApiModelProperty(name = "end date", required = true)
    private Date endDate;
    @ApiModelProperty(name = "program owner", required = true)
    private Long ownerId;
    @ApiModelProperty(name = "developer", required = true)
    private Long developerId;
    @ApiModelProperty(name = "tester", required = true)
    private Long testerId;
}
