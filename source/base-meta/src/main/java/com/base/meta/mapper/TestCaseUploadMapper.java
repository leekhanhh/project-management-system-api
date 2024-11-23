package com.base.meta.mapper;

import com.base.meta.dto.testcaseupload.TestCaseUploadDto;
import com.base.meta.form.testcaseupload.CreateTestCaseUploadForm;
import com.base.meta.form.testcaseupload.UpdateTestCaseUploadForm;
import com.base.meta.model.TestCaseUpload;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProjectMapper.class, ProgramMapper.class})
public interface TestCaseUploadMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "testCasePrecondition", target = "testCasePrecondition")
    @Mapping(source = "testCaseName", target = "testCaseName")
    @Mapping(source = "testCaseMenuPath", target = "testCaseMenuPath")
    @Mapping(source = "testStepAction", target = "testStepAction")
    @Mapping(source = "testStepData", target = "testStepData")
    @Mapping(source = "testStepExpectedResult", target = "testStepExpectedResult")
    @Mapping(source = "program", target = "program", qualifiedByName = "fromEntityToAutoCompleteProgramDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromUpdateTestCaseUploadFormToEntity")
    TestCaseUploadDto fromEntityToTestCaseUploadDto(TestCaseUpload testCaseUpload);

    @IterableMapping(qualifiedByName = "fromEntityToTestCaseUploadDto", elementTargetType = TestCaseUploadDto.class)
    List<TestCaseUploadDto> fromEntityToTestCaseUploadDtoList(List<TestCaseUpload> testCaseUploads);
}
