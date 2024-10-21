package com.base.meta.dto.testdefect;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.category.CategoryDto;
import com.base.meta.dto.teststepexecution.TestStepExecutionDto;
import lombok.Data;

@Data
public class TestDefectDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private String priority;
    private String severity;
    private String description;
    private Boolean sendNotification;
    private CategoryDto status;
    private AccountDto assignedDeveloper;
    private TestStepExecutionDto testStepExecution;

}
