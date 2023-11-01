package com.trnd.trndapi.security.repository;

import com.trnd.trndapi.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByMobile(String mobile);
    List<User> findBySoftDeletedFalse();
    Optional<User> findByMobile(String username);
}
