package com.base.meta.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "flag")
    private Integer flag;
    @ApiModelProperty(name = "modifiedDate")
    private LocalDateTime modifiedDate;
    @ApiModelProperty(name = "createdDate")
    private LocalDateTime createdDate;
}
