package com.example.springsessionsecurity.jpa.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account_role")
public class AccountRole {

    @EmbeddedId
    private AccountRoleKey accountRoleKey;

    public AccountRoleKey getAccountRoleKey() {
        return accountRoleKey;
    }

    public void setAccountRoleKey(AccountRoleKey accountRoleKey) {
        this.accountRoleKey = accountRoleKey;
    }

}
