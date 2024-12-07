package com.base.meta.repository;

import com.base.meta.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findFirstById(Long id);
    Account findAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);

    Account findAccountByPhone(String phone);
}
