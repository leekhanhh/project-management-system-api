package com.base.meta.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ApiModel
public class ForgetPasswordForm {
    @NotEmpty(message = "OTP can not be empty!")
    @ApiModelProperty(name = "otp", required = true)
    private String otp;

    @NotEmpty(message = "idHash can not be empty!")
    @ApiModelProperty(name = "idHash", required = true)
    private String idHash;

    @NotEmpty(message = "newPassword can not be empty!")
    @Size(min = 6, message = "newPassword minimum 6 characters!")
    @ApiModelProperty(name = "newPassword", required = true)
    private String newPassword;
}
