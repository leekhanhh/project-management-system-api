package com.base.meta.dto.project;

import com.base.meta.dto.category.CategoryDto;
import lombok.Data;

@Data
public class ProjectAutoCompleteDto {
    private Long id;
    private String name;
    private CategoryDto status;
}
