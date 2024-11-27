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
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "program type", required = true)
    @NotNull(message = "program type cannot be null")
    private Long programTypeId;
    @ApiModelProperty(name = "status", required = true)
    @NotNull(message = "status cannot be null")
    private Long statusId;
    @ApiModelProperty(name = "program category")
    private String programCategory;
    @ApiModelProperty(name = "start date")
    private Date startDate;
    @ApiModelProperty(name = "end date")
    private Date endDate;
    @ApiModelProperty(name = "program owner")
    private Long ownerId;
    @ApiModelProperty(name = "developer")
    private Long developerId;
    @ApiModelProperty(name = "tester")
    private Long testerId;
    @ApiModelProperty(name = "requirement", required = true)
    @NotNull(message = "requirement cannot be null")
    private Long requirementId;
}
