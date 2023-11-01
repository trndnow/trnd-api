package com.trnd.trndapi.security.repository;

import com.trnd.trndapi.security.entity.Role;
import com.trnd.trndapi.security.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
