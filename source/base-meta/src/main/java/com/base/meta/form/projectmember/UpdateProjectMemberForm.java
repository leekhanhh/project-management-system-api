package com.base.meta.form.projectmember;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateProjectMemberForm {
    @ApiModelProperty(value = "Project Member ID")
    @NotNull(message = "Project Member ID cannot be null!")
    private Long id;
    @ApiModelProperty(value="On-boarded date")
    private String onBoardedDate;
    @ApiModelProperty(value="Off-boarded date")
    private String offBoardedDate;
}
