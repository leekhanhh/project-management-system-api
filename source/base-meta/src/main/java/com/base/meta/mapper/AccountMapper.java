package com.base.meta.mapper;

import com.base.meta.dto.account.AccountAutoCompleteDto;
import com.base.meta.dto.account.AccountAutoCompleteNameDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.model.Account;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class, CategoryMapper.class})
public interface AccountMapper {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "flag", target = "flag")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "memberStatusCategoryId", target = "status.id")
    @Mapping(source = "memberPositionCategoryId", target = "position.id")
    @BeanMapping(ignoreByDefault = true)
    Account fromCreateUserFormToEntity(CreateUserForm createUserForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "status.name", target = "memberStatus")
    @Mapping(source = "position.name", target = "memberPosition")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "displayId", target = "displayId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDto")
    AccountDto fromAccountToDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "status", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Mapping(source = "position", target = "position", qualifiedByName = "fromEntityToAutoCompleteNameToDto")
    @Named("fromAccountToAutoCompleteDto")
    AccountAutoCompleteDto fromAccountToAutoCompleteDto(Account account);

    @IterableMapping(qualifiedByName = "fromAccountToAutoCompleteDto", elementTargetType = AccountAutoCompleteDto.class)
    List<AccountAutoCompleteDto> fromEntityListToAutoCompleteDtoList(List<Account> accounts);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToDto")
    @Named("fromEntityToAccountDtoList")
    List<AccountDto> fromEntityToAccountDtoList(List<Account> accounts);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "displayId", target = "displayId")
    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteAccountNameDto")
    AccountAutoCompleteNameDto fromEntityToAutoCompleteNameToDto(Account account);
}
