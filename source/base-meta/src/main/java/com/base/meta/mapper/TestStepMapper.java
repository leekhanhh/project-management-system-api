package com.base.meta.mapper;

import com.base.meta.dto.teststep.TestStepDto;
import com.base.meta.form.teststep.CreateTestStepForm;
import com.base.meta.form.teststep.UpdateTestStepForm;
import com.base.meta.model.TestCase;
import com.base.meta.model.TestStep;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestCase.class})
public interface TestStepMapper {
    @Mapping(source = "stepNumber", target = "stepNumber")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "data", target = "data")
    @Mapping(source = "expectedResult", target = "expectedResult")
    @BeanMapping(ignoreByDefault = true)
    TestStep fromCreateTestStepFormToEntity(CreateTestStepForm createTestStepForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "stepNumber", target = "stepNumber")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "data", target = "data")
    @Mapping(source = "expectedResult", target = "expectedResult")
    @Mapping(source = "testCase", target = "testCase", qualifiedByName = "fromEntityToTestCaseDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToTestStepDto")
    TestStepDto fromEntityToTestStepDto(TestStep testStep);

    @IterableMapping(qualifiedByName = "fromEntityToTestStepDto", elementTargetType = TestStepDto.class)
    List<TestStepDto> fromEntityToTestStepDtoList(List<TestStep> testSteps);

    @Mapping(source = "action", target = "action")
    @Mapping(source = "data", target = "data")
    @Mapping(source = "expectedResult", target = "expectedResult")
    @BeanMapping(ignoreByDefault = true)
    void updateTestStepFromEntity(UpdateTestStepForm updateTestStepForm, @MappingTarget TestStep testStep);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "stepNumber", target = "stepNumber")
    @Mapping(source = "data", target = "data")
    @Mapping(source = "expectedResult", target = "expectedResult")
    @Named("fromEntityToAutoComplete")
    TestStepDto fromEntityToAutoComplete(TestStep testStep);
}
