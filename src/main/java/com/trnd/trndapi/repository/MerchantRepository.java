package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Query("select count(m) from Merchant m")
    long totalMerchantCount();
    @Query("SELECT m FROM Merchant m where m.merchPriContactEmail = :email")
    Optional<Merchant> findByEmail(String email);

    @Query("SELECT m FROM Merchant  m WHERE m.merchUniqueCode = :userCode")
    Optional<Merchant> findByMerchantUniqueCode(String userCode);

    @Query("SELECT m FROM Merchant m WHERE m.merchantCode = :merchantCode")
    Optional<Merchant> findByMerchantCode(String merchantCode);
}
