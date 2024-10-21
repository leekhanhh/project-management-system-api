package com.base.meta.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class RequestForgetPasswordForm {
    @NotEmpty(message = "email can not be empty!")
    @Email
    @ApiModelProperty(name = "email", required = true)
    private String email;
}
