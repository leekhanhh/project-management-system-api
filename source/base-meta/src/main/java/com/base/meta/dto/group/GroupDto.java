package com.base.meta.dto.group;

import com.base.meta.dto.ABasicAdminDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.model.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GroupDto extends ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "kind")
    private int kind;
    @ApiModelProperty(name = "isSystemRole")
    private Boolean isSystemRole;
    @ApiModelProperty(name = "permissions")
    private List<PermissionDto> permissions;
}
