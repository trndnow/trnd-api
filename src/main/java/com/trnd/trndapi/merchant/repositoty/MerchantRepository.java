package com.trnd.trndapi.merchant.repositoty;

import com.trnd.trndapi.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Query("SELECT m FROM Merchant m where m.merch_pri_contact_email = :email")
    Optional<Merchant> findByEmail(String email);
}
