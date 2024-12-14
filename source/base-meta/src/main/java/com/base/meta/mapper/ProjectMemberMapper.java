package com.base.meta.mapper;

import com.base.meta.dto.projectmember.ProjectMemberDto;
import com.base.meta.form.projectmember.CreateProjectMemberForm;
import com.base.meta.form.projectmember.UpdateProjectMemberForm;
import com.base.meta.model.ProjectMember;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProjectMapper.class, AccountMapper.class})
public interface ProjectMemberMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "project", target = "project", qualifiedByName = "fromEntityToProjectAutoCompleteDto")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromEntityToAccountAutoCompleteDto")
    @Mapping(source = "onBoardedDate", target = "onBoardedDate")
    @Mapping(source = "offBoardedDate", target = "offBoardedDate")
    @BeanMapping(ignoreByDefault = true)
    ProjectMemberDto fromEntityToProjectMemberDto(ProjectMember projectMember);

    @IterableMapping(qualifiedByName = "fromEntityToProjectMemberDto", elementTargetType = ProjectMemberDto.class)
    List<ProjectMemberDto> fromEntityToProjectMemberDtoList(List<ProjectMember> projectMembers);

    @Mapping(source = "onBoardedDate", target = "onBoardedDate")
    @Mapping(source = "offBoardedDate", target = "offBoardedDate")
    @BeanMapping(ignoreByDefault = true)
    void updateProjectMemberFromEntity(UpdateProjectMemberForm updateProjectMemberForm, @MappingTarget ProjectMember projectMember);
}