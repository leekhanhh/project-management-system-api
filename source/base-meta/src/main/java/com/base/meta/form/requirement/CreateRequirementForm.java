package com.base.meta.form.requirement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateRequirementForm {
    @ApiModelProperty(value = "Requirement name", required = true)
    @NotNull(message = "name cannot be null!")
    private Long nameId;
    @ApiModelProperty(value = "Requirement description", required = true)
    private String description;
    @ApiModelProperty(value = "Requirement acceptance", required = true)
    private String acceptance;
    @ApiModelProperty(value = "Devision Id", required = true)
    @NotNull(message = "devision cannot be null!")
    private Long devisionId;
    @ApiModelProperty(value = "Classification Id", required = true)
    private Long classificationId;
    @ApiModelProperty(value = "Project ID", required = true)
    @NotNull(message = "projectId cannot be null!")
    private Long projectId;
}
