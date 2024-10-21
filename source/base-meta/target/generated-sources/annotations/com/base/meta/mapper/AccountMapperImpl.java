package com.base.meta.mapper;

import com.base.meta.dto.account.AccountAutoCompleteDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-07T13:58:13+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Account fromCreateUserFormToEntity(CreateUserForm createCareerForm) {
        if ( createCareerForm == null ) {
            return null;
        }

        Account account = new Account();

        account.setAvatarPath( createCareerForm.getAvatarPath() );
        account.setFullName( createCareerForm.getFullName() );
        account.setPhone( createCareerForm.getPhone() );
        account.setEmail( createCareerForm.getEmail() );
        account.setUsername( createCareerForm.getUsername() );
        if ( createCareerForm.getStatus() != null ) {
            account.setStatus( createCareerForm.getStatus() );
        }

        return account;
    }

    @Override
    public AccountDto fromAccountToDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setLastLogin( account.getLastLogin() );
        accountDto.setKind( account.getKind() );
        accountDto.setFullName( account.getFullName() );
        accountDto.setIsSuperAdmin( account.getIsSuperAdmin() );
        accountDto.setAvatar( account.getAvatarPath() );
        accountDto.setPhone( account.getPhone() );
        accountDto.setId( account.getId() );
        accountDto.setEmail( account.getEmail() );
        accountDto.setUsername( account.getUsername() );
        accountDto.setGroup( groupMapper.fromEntityToGroupDtoAutoComplete( account.getGroup() ) );

        return accountDto;
    }

    @Override
    public AccountAutoCompleteDto fromAccountToAutoCompleteDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountAutoCompleteDto accountAutoCompleteDto = new AccountAutoCompleteDto();

        accountAutoCompleteDto.setAvatarPath( account.getAvatarPath() );
        accountAutoCompleteDto.setFullName( account.getFullName() );
        if ( account.getId() != null ) {
            accountAutoCompleteDto.setId( account.getId() );
        }

        return accountAutoCompleteDto;
    }

    @Override
    public List<AccountAutoCompleteDto> convertAccountToAutoCompleteDto(List<Account> list) {
        if ( list == null ) {
            return null;
        }

        List<AccountAutoCompleteDto> list1 = new ArrayList<AccountAutoCompleteDto>( list.size() );
        for ( Account account : list ) {
            list1.add( accountToAccountAutoCompleteDto( account ) );
        }

        return list1;
    }

    @Override
    public List<AccountDto> fromEntityToAccountDtoList(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountDto> list = new ArrayList<AccountDto>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( fromAccountToDto( account ) );
        }

        return list;
    }

    protected AccountAutoCompleteDto accountToAccountAutoCompleteDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountAutoCompleteDto accountAutoCompleteDto = new AccountAutoCompleteDto();

        if ( account.getId() != null ) {
            accountAutoCompleteDto.setId( account.getId() );
        }
        accountAutoCompleteDto.setFullName( account.getFullName() );
        accountAutoCompleteDto.setAvatarPath( account.getAvatarPath() );

        return accountAutoCompleteDto;
    }
}
