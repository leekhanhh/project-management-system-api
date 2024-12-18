package com.base.meta.mapper;

import com.base.meta.dto.account.user.UserDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.form.user.UpdateUserForm;
import com.base.meta.form.user.UpdateUserProfileForm;
import com.base.meta.model.Account;
import com.base.meta.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T18:09:19+0700",
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
        userDto.setAccount( accountMapper.fromAccountToDto( user.getAccount() ) );

        return userDto;
    }

    @Override
    public UserDto fromEntityToUserAccountDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setAccount( accountMapper.fromAccountToDto( user.getAccount() ) );

        return userDto;
    }

    @Override
    public List<UserDto> fromEntityToUserDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( fromEntityToUserDto( user ) );
        }

        return list;
    }

    @Override
    public List<UserDto> fromEntityToUserAccountDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( fromEntityToUserAccountDto( user ) );
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

    protected void updateUserFormToAccount(UpdateUserForm updateUserForm, Account mappingTarget) {
        if ( updateUserForm == null ) {
            return;
        }

        if ( updateUserForm.getFullName() != null ) {
            mappingTarget.setFullName( updateUserForm.getFullName() );
        }
        if ( updateUserForm.getFlag() != null ) {
            mappingTarget.setFlag( updateUserForm.getFlag() );
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
