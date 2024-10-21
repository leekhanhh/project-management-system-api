package com.base.meta.dto.project;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.category.CategoryDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectDto extends ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "startDate")
    private Date startDate;
    @ApiModelProperty(name = "endDate")
    private Date endDate;
    @ApiModelProperty(name = "status")
    private CategoryDto status;
}
