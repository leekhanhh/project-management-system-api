package com.base.meta.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserForm {
    @NotNull(message = "id cant not be null!")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "fullName cant not be empty!")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @NotEmpty(message = "phone cant not be empty!")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;
    @ApiModelProperty(name = "member status")
    private Long memberStatusCategoryId;
    @ApiModelProperty(name = "member position", required = true)
    @NotNull(message = "member position cant not be null")
    private Long memberPositionCategoryId;
    @NotEmpty(message = "firstname can not be empty!")
    @ApiModelProperty(name = "firstName", required = true)
    private String firstName;
    @NotEmpty(message = "lastname can not be empty!")
    @ApiModelProperty(name = "lastName", required = true)
    private String lastName;
    @ApiModelProperty(name = "logoPath")
    private String logoPath;
    @NotNull(message = "status cant not be null!")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}
