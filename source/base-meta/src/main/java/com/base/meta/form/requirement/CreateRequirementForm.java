package com.base.meta.form.requirement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateRequirementForm {
    @ApiModelProperty(value = "Requirement name", required = true)
    @NotEmpty(message = "name cannot be null!")
    private String name;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Devision Id", required = true)
    @NotEmpty(message = "devision cannot be null!")
    private Long devisionId;
    @ApiModelProperty(value = "Acceptance Id", required = true)
    private Long acceptanceId;
    @ApiModelProperty(value = "Classification Id", required = true)
    private Long classificationId;
    @ApiModelProperty(value = "Project ID", required = true)
    @NotEmpty(message = "projectId cannot be null!")
    private Long projectId;
}
