package com.base.meta.dto.account.user;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.model.Group;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String avatarPath;
    private Group group;
    private String status;
    private String position;
    private AccountDto accountDto;
    private Integer flag;
}
