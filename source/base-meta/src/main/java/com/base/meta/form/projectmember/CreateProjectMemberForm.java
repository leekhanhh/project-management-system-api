package com.base.meta.form.projectmember;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateProjectMemberForm {
    @ApiModelProperty(value = "Project ID")
    @NotNull(message = "Project ID cannot be null!")
    private Long projectId;
    @ApiModelProperty(value = "Account ID")
    @NotNull(message = "Account ID cannot be null!")
    private Long accountId;
    @ApiModelProperty(value = "On boarded date")
    @FutureOrPresent(message = "On boarded date must be in the present or future!")
    private Date onBoardedDate;
    @ApiModelProperty(value = "Off boarded date")
    @FutureOrPresent(message = "Off boarded date must be in the present or future!")
    private Date offBoardedDate;
}
