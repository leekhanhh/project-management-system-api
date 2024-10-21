package com.base.meta.dto.requirement;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.project.ProjectDto;
import lombok.Data;

@Data
public class RequirementDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private String description;
    private CategoryDto devision;
    private CategoryDto detailClassification;
    private CategoryDto acceptance;
    private ProjectDto project;
}
