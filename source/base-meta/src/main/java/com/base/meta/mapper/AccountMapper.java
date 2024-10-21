package com.base.meta.mapper;

import com.base.meta.dto.account.AccountAutoCompleteDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.model.Account;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
public interface AccountMapper {
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "flag", target = "flag")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "memberStatusCategoryId", target = "category.id")
    @Mapping(source = "memberPositionCategoryId", target = "category.id")
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
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDto")
    AccountDto fromAccountToDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "status.name", target = "memberStatus")
    @Mapping(source = "position.name", target = "memberPosition")
    @Named("fromAccountToAutoCompleteDto")
    AccountAutoCompleteDto fromAccountToAutoCompleteDto(Account account);


    @IterableMapping(elementTargetType = AccountAutoCompleteDto.class)
    List<AccountAutoCompleteDto> convertAccountToAutoCompleteDto(List<Account> list);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToDto")
    @Named("fromEntityToAccountDtoList")
    List<AccountDto> fromEntityToAccountDtoList(List<Account> accounts);



}
