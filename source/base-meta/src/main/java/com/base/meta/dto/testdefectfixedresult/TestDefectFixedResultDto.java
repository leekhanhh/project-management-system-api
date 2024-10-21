package com.base.meta.dto.testdefectfixedresult;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.testdefect.TestDefectDto;
import lombok.Data;

import java.util.Date;

@Data
public class TestDefectFixedResultDto extends ABasicAdminDto {
    private Long id;
    private TestDefectDto testDefect;
    private String description;
    private Date fixedDate;
    private String remark;
}
