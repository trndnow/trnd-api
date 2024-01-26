package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Query("select u from User u where u.id in ?1 and u.userStatus = ?2 order by u.registrationDateTime")
    List<User> findByIdInAndUserStatusOrderByRegistrationDateTimeAsc(Collection<Long> ids, AccountStatus userStatus);

    @Query("select u from User u where u.uniqueId in ?1 and u.userStatus = ?2 order by u.registrationDateTime")
    List<User> findByUniqueIdInAndUserStatusOrderByRegistrationDateTimeAsc(Collection<UUID> uniqueIds, AccountStatus userStatus);
}
