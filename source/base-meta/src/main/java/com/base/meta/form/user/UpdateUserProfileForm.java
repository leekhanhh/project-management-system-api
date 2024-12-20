package com.base.meta.form.user;

import com.base.meta.validation.NumberField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UpdateUserProfileForm {
    @ApiModelProperty(name = "email" )
    @Email
    private String email;
    @ApiModelProperty(name = "password" )
    private String password;
    @NotEmpty(message = "oldPassword cant not be empty!")
    @ApiModelProperty(name = "oldPassword", required = true)
    private String oldPassword;

    @NotEmpty(message = "position can not be empty!")
    @ApiModelProperty(name = "position", required = true)
    private String position;
    @NotEmpty(message = "firstname can not be empty!")
    @ApiModelProperty(name = "firstName", required = true)
    private String firstName;
    @NotEmpty(message = "lastname can not be empty!")
    @ApiModelProperty(name = "lastName", required = true)
    private String lastName;
    @ApiModelProperty(name = "phone", required = true)
    @NumberField
    private String phone;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath ;
}
