package com.base.meta.mapper;

import com.base.meta.dto.account.user.UserDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.form.user.UpdateUserForm;
import com.base.meta.form.user.UpdateUserProfileForm;
import com.base.meta.model.Account;
import com.base.meta.model.Group;
import com.base.meta.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-26T14:24:03+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public User fromCreateUserFormToEntity(CreateUserForm createUserForm) {
        if ( createUserForm == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( createUserForm.getFirstName() );
        user.setLastName( createUserForm.getLastName() );
        user.setAvatarPath( createUserForm.getAvatarPath() );

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
        userDto.setFlag( user.getFlag() );
        userDto.setGroup( userAccountGroup( user ) );
        userDto.setAccountDto( accountMapper.fromAccountToDto( user.getAccount() ) );

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
        if ( updateUserForm.getFirstName() != null ) {
            user.setFirstName( updateUserForm.getFirstName() );
        }
        if ( updateUserForm.getLastName() != null ) {
            user.setLastName( updateUserForm.getLastName() );
        }
        if ( updateUserForm.getStatus() != null ) {
            user.setFlag( updateUserForm.getStatus() );
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

        if ( updateUserProfileForm.getEmail() != null ) {
            mappingTarget.setEmail( updateUserProfileForm.getEmail() );
        }
        if ( updateUserProfileForm.getPhone() != null ) {
            mappingTarget.setPhone( updateUserProfileForm.getPhone() );
        }
    }
}
