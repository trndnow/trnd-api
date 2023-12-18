package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {
    @Query("SELECT a FROM Affiliate a where a.affContactEmail = :email")
    Optional<Affiliate> findByEmail(String email);
}