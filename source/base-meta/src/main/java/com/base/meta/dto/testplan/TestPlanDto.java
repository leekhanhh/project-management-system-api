package com.base.meta.dto.testplan;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.program.ProgramDto;
import com.base.meta.dto.project.ProjectDto;
import lombok.Data;

@Data
public class TestPlanDto extends ABasicAdminDto {
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private ProgramDto program;
    private String displayId;
}
