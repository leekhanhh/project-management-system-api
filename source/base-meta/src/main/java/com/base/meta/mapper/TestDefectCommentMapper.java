package com.base.meta.mapper;

import com.base.meta.dto.testdefectcomment.TestDefectCommentDto;
import com.base.meta.form.testdefectcomment.CreateTestDefectCommentForm;
import com.base.meta.form.testdefectcomment.UpdateTestDefectCommentForm;
import com.base.meta.model.TestDefectComment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, TestDefectMapper.class})
public interface TestDefectCommentMapper {

    @Mapping(source = "comment", target = "comment")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTestDefectCommentFormToEntity")
    TestDefectComment fromCreateTestDefectCommentFormToEntity(CreateTestDefectCommentForm createTestDefectCommentForm);

    @Mapping(source = "comment", target = "comment")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateTestDefectCommentFormToEntity(UpdateTestDefectCommentForm updateTestDefectCommentForm, @MappingTarget TestDefectComment testDefectComment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "testDefect", target = "testDefect", qualifiedByName = "fromEntityToDtoAutoComplete")
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "sender", target = "sender", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    TestDefectCommentDto fromEntityToDto(TestDefectComment testDefectComment);

    @IterableMapping(qualifiedByName = "fromEntityToDto", elementTargetType = TestDefectCommentDto.class)
    List<TestDefectCommentDto> fromEntityToDtoList(List<TestDefectComment> testDefectComments);
}
