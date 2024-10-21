package com.base.meta.dto.account;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDto extends ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "kind")
    private int kind;
    @ApiModelProperty(name = "username")
    private String username;
    @ApiModelProperty(name = "phone")
    private String phone;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "member status")
    private String memberStatus;
    @ApiModelProperty(name = "member position")
    private String memberPosition;
    @ApiModelProperty(name = "group")
    private GroupDto group;
    @ApiModelProperty(name = "lastLogin")
    private Date lastLogin;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    private Boolean isSuperAdmin;
}
