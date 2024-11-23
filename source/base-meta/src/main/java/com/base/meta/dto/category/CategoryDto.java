package com.base.meta.dto.category;

import com.base.meta.dto.ABasicAdminDto;
import lombok.Data;

@Data
public class CategoryDto extends ABasicAdminDto {
    private Long id;
    private Integer categoryKind;
    private String categoryName;
    private String categoryCode;
    private String categoryDescription;
    private Integer categoryOrdering;
    private Long parentId;
    private String displayId;
}
