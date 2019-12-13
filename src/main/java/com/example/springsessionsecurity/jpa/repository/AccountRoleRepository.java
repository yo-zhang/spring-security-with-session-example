package com.example.springsessionsecurity.jpa.repository;

import com.example.springsessionsecurity.jpa.entity.AccountRole;
import com.example.springsessionsecurity.jpa.entity.AccountRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole, AccountRoleKey> {

    List<AccountRole> findByAccountRoleKeyAccountId(String accountId);

}
