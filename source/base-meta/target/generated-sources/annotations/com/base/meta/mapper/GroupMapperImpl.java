package com.base.meta.mapper;

import com.base.meta.dto.group.GroupDto;
import com.base.meta.form.group.CreateGroupForm;
import com.base.meta.model.Group;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T17:54:45+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Group fromCreateGroupFormToEntity(CreateGroupForm createGroupForm) {
        if ( createGroupForm == null ) {
            return null;
        }

        Group group = new Group();

        if ( createGroupForm.getKind() != null ) {
            group.setKind( createGroupForm.getKind() );
        }
        group.setDescription( createGroupForm.getDescription() );
        group.setName( createGroupForm.getName() );

        return group;
    }

    @Override
    public GroupDto fromEntityToGroupDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        if ( group.getCreatedDate() != null ) {
            groupDto.setCreatedDate( LocalDateTime.ofInstant( group.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setFlag( group.getFlag() );
        groupDto.setIsSystemRole( group.getIsSystemRole() );
        groupDto.setKind( group.getKind() );
        groupDto.setPermissions( permissionMapper.fromEntityToPermissionDtoShortList( group.getPermissions() ) );
        groupDto.setName( group.getName() );
        if ( group.getModifiedDate() != null ) {
            groupDto.setModifiedDate( LocalDateTime.ofInstant( group.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setDescription( group.getDescription() );
        groupDto.setId( group.getId() );
        groupDto.setDisplayId( group.getDisplayId() );

        return groupDto;
    }

    @Override
    public List<GroupDto> fromEntityToGroupDtoList(List<Group> groups) {
        if ( groups == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( groups.size() );
        for ( Group group : groups ) {
            list.add( fromEntityToGroupDto( group ) );
        }

        return list;
    }

    @Override
    public GroupDto fromEntityToGroupDtoShort(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setKind( group.getKind() );
        groupDto.setPermissions( permissionMapper.fromEntityToPermissionDtoShortList( group.getPermissions() ) );
        groupDto.setName( group.getName() );
        groupDto.setDescription( group.getDescription() );
        groupDto.setId( group.getId() );

        return groupDto;
    }

    @Override
    public GroupDto fromEntityToGroupDtoAutoComplete(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setKind( group.getKind() );
        groupDto.setName( group.getName() );
        groupDto.setId( group.getId() );
        groupDto.setDisplayId( group.getDisplayId() );

        return groupDto;
    }

    @Override
    public List<GroupDto> fromEntityToGroupDtoAutoCompleteList(List<Group> groups) {
        if ( groups == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( groups.size() );
        for ( Group group : groups ) {
            list.add( fromEntityToGroupDtoAutoComplete( group ) );
        }

        return list;
    }
}
