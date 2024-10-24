package com.base.meta.mapper;

import com.base.meta.dto.account.AccountAutoCompleteDto;
import com.base.meta.dto.account.AccountDto;
import com.base.meta.form.user.CreateUserForm;
import com.base.meta.model.Account;
import com.base.meta.model.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-24T19:17:12+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Account fromCreateUserFormToEntity(CreateUserForm createUserForm) {
        if ( createUserForm == null ) {
            return null;
        }

        Account account = new Account();

        account.setStatus( createUserFormToCategory( createUserForm ) );
        account.setPosition( createUserFormToCategory1( createUserForm ) );
        if ( createUserForm.getFlag() != null ) {
            account.setFlag( createUserForm.getFlag() );
        }
        account.setAvatarPath( createUserForm.getAvatarPath() );
        account.setPhone( createUserForm.getPhone() );
        account.setEmail( createUserForm.getEmail() );
        account.setUsername( createUserForm.getUsername() );

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
        accountDto.setMemberStatus( accountStatusName( account ) );
        accountDto.setIsSuperAdmin( account.getIsSuperAdmin() );
        accountDto.setAvatar( account.getAvatarPath() );
        accountDto.setMemberPosition( accountPositionName( account ) );
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
        accountAutoCompleteDto.setPosition( categoryMapper.fromEntityToAutoCompleteNameToDto( account.getPosition() ) );
        accountAutoCompleteDto.setStatus( categoryMapper.fromEntityToAutoCompleteNameToDto( account.getStatus() ) );

        return accountAutoCompleteDto;
    }

    @Override
    public List<AccountAutoCompleteDto> fromEntityListToAutoCompleteDtoList(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountAutoCompleteDto> list = new ArrayList<AccountAutoCompleteDto>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( fromAccountToAutoCompleteDto( account ) );
        }

        return list;
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

    protected Category createUserFormToCategory(CreateUserForm createUserForm) {
        if ( createUserForm == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( createUserForm.getMemberStatusCategoryId() );

        return category;
    }

    protected Category createUserFormToCategory1(CreateUserForm createUserForm) {
        if ( createUserForm == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( createUserForm.getMemberPositionCategoryId() );

        return category;
    }

    private String accountStatusName(Account account) {
        if ( account == null ) {
            return null;
        }
        Category status = account.getStatus();
        if ( status == null ) {
            return null;
        }
        String name = status.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String accountPositionName(Account account) {
        if ( account == null ) {
            return null;
        }
        Category position = account.getPosition();
        if ( position == null ) {
            return null;
        }
        String name = position.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
