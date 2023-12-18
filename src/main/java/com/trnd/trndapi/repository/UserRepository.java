package com.trnd.trndapi.repository;

import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserStatusAndRole_NameOrderByRegistrationDateTimeAsc(AccountStatus userStatus, ERole name, Sort sort);

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByMobile(String mobile);
    List<User> findBySoftDeletedFalse();
    Optional<User> findByMobile(String username);
    List<User> findByUserStatus(AccountStatus userStatus);
    @Query("SELECT u.userCode FROM User u")
    List<String> findAllUserCode();
//    @Query("SELECT u FROM User u WHERE u.role.name =: role")
    Boolean existsUserByRoleName(ERole role);
}
