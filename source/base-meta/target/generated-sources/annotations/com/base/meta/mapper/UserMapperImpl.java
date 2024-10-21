package com.base.meta.mapper;

import com.base.meta.dto.account.AccountDto;
import com.base.meta.dto.account.user.UserDto;
import com.base.meta.dto.group.GroupDto;
import com.base.meta.dto.permission.PermissionDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.form.user.UpdateUserForm;
import com.base.meta.form.user.UpdateUserProfileForm;
import com.base.meta.model.Account;
import com.base.meta.model.Group;
import com.base.meta.model.Permission;
import com.base.meta.model.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-07T13:58:12+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromCreateUserFormToEntity(CreateUserForm createUserForm) {
        if ( createUserForm == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( createUserForm.getFirstName() );
        user.setLastName( createUserForm.getLastName() );
        user.setAvatarPath( createUserForm.getAvatarPath() );
        user.setPosition( createUserForm.getPosition() );

        return user;
    }

    @Override
    public UserDto fromEntityToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setPosition( user.getPosition() );
        userDto.setGroup( userAccountGroup( user ) );
        userDto.setStatus( user.getStatus() );
        userDto.setAccountDto( accountToAccountDto( user.getAccount() ) );

        return userDto;
    }

    @Override
    public List<UserDto> fromEntityToUserDtoList(List<User> careers) {
        if ( careers == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( careers.size() );
        for ( User user : careers ) {
            list.add( fromEntityToUserDto( user ) );
        }

        return list;
    }

    @Override
    public void updateUserFromEntity(UpdateUserForm updateUserForm, User user) {
        if ( updateUserForm == null ) {
            return;
        }

        if ( user.getAccount() == null ) {
            user.setAccount( new Account() );
        }
        updateUserFormToAccount( updateUserForm, user.getAccount() );
        if ( updateUserForm.getLastName() != null ) {
            user.setLastName( updateUserForm.getLastName() );
        }
        if ( updateUserForm.getFirstName() != null ) {
            user.setFirstName( updateUserForm.getFirstName() );
        }
        if ( updateUserForm.getPosition() != null ) {
            user.setPosition( updateUserForm.getPosition() );
        }
        if ( updateUserForm.getStatus() != null ) {
            user.setStatus( updateUserForm.getStatus() );
        }
    }

    @Override
    public void updateUserProfileFromEntity(UpdateUserProfileForm updateUserProfileForm, User user) {
        if ( updateUserProfileForm == null ) {
            return;
        }

        if ( user.getAccount() == null ) {
            user.setAccount( new Account() );
        }
        updateUserProfileFormToAccount( updateUserProfileForm, user.getAccount() );
        if ( updateUserProfileForm.getFirstName() != null ) {
            user.setFirstName( updateUserProfileForm.getFirstName() );
        }
        if ( updateUserProfileForm.getLastName() != null ) {
            user.setLastName( updateUserProfileForm.getLastName() );
        }
        if ( updateUserProfileForm.getPosition() != null ) {
            user.setPosition( updateUserProfileForm.getPosition() );
        }
        if ( updateUserProfileForm.getAvatarPath() != null ) {
            user.setAvatarPath( updateUserProfileForm.getAvatarPath() );
        }
    }

    private Group userAccountGroup(User user) {
        if ( user == null ) {
            return null;
        }
        Account account = user.getAccount();
        if ( account == null ) {
            return null;
        }
        Group group = account.getGroup();
        if ( group == null ) {
            return null;
        }
        return group;
    }

    protected PermissionDto permissionToPermissionDto(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDto permissionDto = new PermissionDto();

        permissionDto.setId( permission.getId() );
        permissionDto.setStatus( permission.getStatus() );
        if ( permission.getModifiedDate() != null ) {
            permissionDto.setModifiedDate( LocalDateTime.ofInstant( permission.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( permission.getCreatedDate() != null ) {
            permissionDto.setCreatedDate( LocalDateTime.ofInstant( permission.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        permissionDto.setName( permission.getName() );
        permissionDto.setAction( permission.getAction() );
        permissionDto.setShowMenu( permission.getShowMenu() );
        permissionDto.setDescription( permission.getDescription() );
        permissionDto.setNameGroup( permission.getNameGroup() );
        permissionDto.setPermissionCode( permission.getPermissionCode() );
        permissionDto.setKind( permission.getKind() );

        return permissionDto;
    }

    protected List<PermissionDto> permissionListToPermissionDtoList(List<Permission> list) {
        if ( list == null ) {
            return null;
        }

        List<PermissionDto> list1 = new ArrayList<PermissionDto>( list.size() );
        for ( Permission permission : list ) {
            list1.add( permissionToPermissionDto( permission ) );
        }

        return list1;
    }

    protected GroupDto groupToGroupDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setId( group.getId() );
        groupDto.setStatus( group.getStatus() );
        if ( group.getModifiedDate() != null ) {
            groupDto.setModifiedDate( LocalDateTime.ofInstant( group.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( group.getCreatedDate() != null ) {
            groupDto.setCreatedDate( LocalDateTime.ofInstant( group.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        groupDto.setName( group.getName() );
        groupDto.setDescription( group.getDescription() );
        groupDto.setKind( group.getKind() );
        groupDto.setIsSystemRole( group.getIsSystemRole() );
        groupDto.setPermissions( permissionListToPermissionDtoList( group.getPermissions() ) );

        return groupDto;
    }

    protected AccountDto accountToAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setId( account.getId() );
        accountDto.setStatus( account.getStatus() );
        if ( account.getModifiedDate() != null ) {
            accountDto.setModifiedDate( LocalDateTime.ofInstant( account.getModifiedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( account.getCreatedDate() != null ) {
            accountDto.setCreatedDate( LocalDateTime.ofInstant( account.getCreatedDate().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        accountDto.setKind( account.getKind() );
        accountDto.setUsername( account.getUsername() );
        accountDto.setPhone( account.getPhone() );
        accountDto.setEmail( account.getEmail() );
        accountDto.setFullName( account.getFullName() );
        accountDto.setGroup( groupToGroupDto( account.getGroup() ) );
        accountDto.setLastLogin( account.getLastLogin() );
        accountDto.setIsSuperAdmin( account.getIsSuperAdmin() );

        return accountDto;
    }

    protected void updateUserFormToAccount(UpdateUserForm updateUserForm, Account mappingTarget) {
        if ( updateUserForm == null ) {
            return;
        }

        if ( updateUserForm.getFullName() != null ) {
            mappingTarget.setFullName( updateUserForm.getFullName() );
        }
        if ( updateUserForm.getPhone() != null ) {
            mappingTarget.setPhone( updateUserForm.getPhone() );
        }
    }

    protected void updateUserProfileFormToAccount(UpdateUserProfileForm updateUserProfileForm, Account mappingTarget) {
        if ( updateUserProfileForm == null ) {
            return;
        }

        if ( updateUserProfileForm.getFullName() != null ) {
            mappingTarget.setFullName( updateUserProfileForm.getFullName() );
        }
        if ( updateUserProfileForm.getEmail() != null ) {
            mappingTarget.setEmail( updateUserProfileForm.getEmail() );
        }
        if ( updateUserProfileForm.getPhone() != null ) {
            mappingTarget.setPhone( updateUserProfileForm.getPhone() );
        }
    }
}
