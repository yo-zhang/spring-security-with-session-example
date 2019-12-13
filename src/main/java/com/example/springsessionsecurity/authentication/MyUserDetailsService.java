package com.example.springsessionsecurity.authentication;

import com.example.springsessionsecurity.jpa.entity.Account;
import com.example.springsessionsecurity.jpa.entity.AccountRole;
import com.example.springsessionsecurity.jpa.repository.AccountRepository;
import com.example.springsessionsecurity.jpa.repository.AccountRoleRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;

    public MyUserDetailsService(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<AccountRole> accountRoles = accountRoleRepository.findByAccountRoleKeyAccountId(account.getId());
        if (accountRoles.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        String[] roles = new String[accountRoles.size()];
        for (int i = 0; i < accountRoles.size(); i++) {
            roles[i] = accountRoles.get(i).getAccountRoleKey().getRoleName();
        }

        return User.builder()
                .username(account.getId())
                .password(account.getPassword())
                .roles(roles)
                .build();
    }

}
