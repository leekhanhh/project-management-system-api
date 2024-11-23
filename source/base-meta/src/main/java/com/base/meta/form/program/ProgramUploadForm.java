package com.base.meta.form.program;

import lombok.Data;

import java.util.Date;
@Data
public class ProgramUploadForm{
    private String projectId;
    private String programName;
    private String programCategory;
    private String programType;
    private String requirementId;
    private Date startDate;
    private Date endDate;
    private String description;
}
