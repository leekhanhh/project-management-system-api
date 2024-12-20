package com.base.meta.dto.projectmember;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.project.ProjectDto;
import lombok.Data;

@Data
public class ProjectMemberDto extends ABasicAdminDto {
    private Long id;
    private ProjectDto project;
    private AccountDto account;
    private String onBoardedDate;
    private String offBoardedDate;
}
