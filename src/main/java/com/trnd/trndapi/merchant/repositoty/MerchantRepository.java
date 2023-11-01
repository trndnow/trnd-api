package com.trnd.trndapi.merchant.repositoty;

import com.trnd.trndapi.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
