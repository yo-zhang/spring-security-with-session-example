package com.example.springsessionsecurity.jpa.repository;

import com.example.springsessionsecurity.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByPhone(String phone);
}
