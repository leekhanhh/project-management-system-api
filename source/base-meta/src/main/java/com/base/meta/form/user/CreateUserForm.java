package com.base.meta.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserForm {
    @NotEmpty(message = "username can not be empty!")
    @ApiModelProperty(name = "username", required = true)
    private String username;
    @Email
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @NotEmpty(message = "password can not be empty!")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotEmpty(message = "fullName can not be empty!")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @NotEmpty(message = "phone can not be empty!")
    @ApiModelProperty(name = "phone", required = true)
    private String phone;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "member status")
    private Long memberStatusCategoryId;
    @ApiModelProperty(name = "member position", required = true)
    @NotNull(message = "member position cant not be null")
    private Long memberPositionCategoryId;
    @NotNull(message = "user kind can not be null!")
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;
    @NotEmpty(message = "firstname can not be empty!")
    @ApiModelProperty(name = "firstName", required = true)
    private String firstName;
    @NotEmpty(message = "lastname can not be empty!")
    @ApiModelProperty(name = "lastName", required = true)
    private String lastName;
    @NotEmpty(message = "logoPath cant not be empty!")
    @ApiModelProperty(name = "logoPath", required = true)
    private String logoPath;
    @NotNull(message = "status can not be null!")
    @ApiModelProperty(name = "status", required = true)
    private Integer flag;
}
