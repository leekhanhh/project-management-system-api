package com.base.meta.dto.account;

import com.base.meta.dto.category.CategoryDto;
import lombok.Data;

@Data
public class AccountAutoCompleteDto {
    private long id;
    private String fullName;
    private String avatarPath;
    private CategoryDto status;
    private CategoryDto position;
}
