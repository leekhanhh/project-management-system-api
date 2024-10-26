package com.base.meta.mapper;

import com.base.meta.dto.group.GroupDto;
import com.base.meta.form.group.CreateGroupForm;
import com.base.meta.model.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PermissionMapper.class})
public interface GroupMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    Group fromCreateGroupFormToEntity(CreateGroupForm createGroupForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "isSystemRole", target = "isSystemRole")
    @Mapping(source = "permissions", target = "permissions", qualifiedByName= "fromEntityToPermissionDtoShortList")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "flag", target = "flag")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupDto")
    GroupDto fromEntityToGroupDto(Group group);

    @IterableMapping(elementTargetType = GroupDto.class, qualifiedByName = "fromEntityToGroupDto")
    @Named("fromEntityToGroupDtoList")
    List<GroupDto> fromEntityToGroupDtoList(List<Group> groups);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "permissions", target = "permissions", qualifiedByName = "fromEntityToPermissionDtoShortList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupDtoShort")
    GroupDto fromEntityToGroupDtoShort(Group group);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToGroupDtoAutoComplete")
    GroupDto fromEntityToGroupDtoAutoComplete(Group group);

    @IterableMapping(elementTargetType = GroupDto.class, qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    List<GroupDto> fromEntityToGroupDtoAutoCompleteList(List<Group> groups);
}
