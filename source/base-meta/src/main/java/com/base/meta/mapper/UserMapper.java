package com.base.meta.mapper;

import com.base.meta.dto.account.user.UserDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.form.user.UpdateUserForm;
import com.base.meta.form.user.UpdateUserProfileForm;
import com.base.meta.model.User;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class, AccountMapper.class})
public interface UserMapper {
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @BeanMapping(ignoreByDefault = true)
    User fromCreateUserFormToEntity(CreateUserForm createUserForm);

//    @Mapping(source = "tenantId", target = "tenantId")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "account.group", target = "group")
    @Mapping(source = "flag", target = "flag")
    @Mapping(source = "account", target = "accountDto", qualifiedByName = "fromAccountToAutoCompleteDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToUserDto")
    UserDto fromEntityToUserDto(User user);

    @IterableMapping(qualifiedByName = "fromEntityToUserDto", elementTargetType = UserDto.class)
    List<UserDto> fromEntityToUserDtoList(List<User> careers);

    @Mapping(target = "account.fullName", source = "fullName")
    @Mapping(target = "account.phone", source = "phone")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(target = "flag", source = "status")
    @BeanMapping(ignoreByDefault = true)
    void updateUserFromEntity(UpdateUserForm updateUserForm, @MappingTarget User user);

    @Mapping(target = "account.email", source = "email")
    @Mapping(target = "account.fullName", source = "fullName")
    @Mapping(target = "account.phone", source = "phone")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    void updateUserProfileFromEntity(UpdateUserProfileForm updateUserProfileForm, @MappingTarget User user);
}
