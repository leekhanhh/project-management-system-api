package com.base.meta.mapper;

import com.base.meta.dto.testdefectfixedresult.TestDefectFixedResultDto;
import com.base.meta.form.test.defect.fixed.result.CreateTestDefectFixedResultForm;
import com.base.meta.form.test.defect.fixed.result.UpdateTestDefectFixedResultForm;
import com.base.meta.model.TestDefectFixedResult;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TestDefectMapper.class})
public interface TestDefectFixedResultMapper {
    @Mapping(source = "actionStartDate", target = "actionStartDate")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "remark", target = "remark")
    @BeanMapping(ignoreByDefault = true)
    TestDefectFixedResult fromCreateTestDefectFixedResultFormToEntity(CreateTestDefectFixedResultForm createTestDefectFixedResultForm);

    @Mapping(source = "actionStartDate", target = "actionStartDate")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "remark", target = "remark")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateTestDefectFixedResultFormToEntity(UpdateTestDefectFixedResultForm updateTestDefectFixedResultForm, @MappingTarget TestDefectFixedResult testDefectFixedResult);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "testDefect", target = "testDefect", qualifiedByName = "fromEntityToDtoAutoComplete")
    @Mapping(source = "actionStartDate", target = "fixedDate")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "remark", target = "remark")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestDefectFixedResultDto fromEntityToDto(TestDefectFixedResult testDefectFixedResult);

    @IterableMapping(elementTargetType = TestDefectFixedResultDto.class, qualifiedByName = "fromEntityToDto")
    List<TestDefectFixedResultDto> fromEntityListToDtoList(List<TestDefectFixedResult> testDefectFixedResultList);
}
