package com.base.meta.form.testdefectcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTestDefectCommentForm {
    @ApiModelProperty(value = "Test defect id", required = true)
    @NotNull(message = "Test defect id is required")
    private Long testDefectId;
    @ApiModelProperty(value = "title", required = true)
    @NotEmpty(message = "title is required")
    private String title;
    @ApiModelProperty(value = "comment", required = true)
    @NotEmpty(message = "comment is required")
    private String comment;
    @ApiModelProperty(value = "sender id", required = true)
    @NotNull(message = "sender id is required")
    private Long senderId;
    @ApiModelProperty(value="sender token")
    @JsonIgnore
    private String senderToken;
}
