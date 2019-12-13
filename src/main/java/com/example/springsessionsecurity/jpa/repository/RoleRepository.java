package com.example.springsessionsecurity.jpa.repository;

import com.example.springsessionsecurity.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
