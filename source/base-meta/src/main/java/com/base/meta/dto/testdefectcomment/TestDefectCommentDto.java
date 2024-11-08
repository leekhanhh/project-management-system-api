package com.base.meta.dto.testdefectcomment;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.testdefect.TestDefectDto;
import lombok.Data;

@Data
public class TestDefectCommentDto extends ABasicAdminDto {
    private Long id;
    private String title;
    private String comment;
    private TestDefectDto testDefect;
    private AccountDto sender;
}
