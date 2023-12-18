package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.BusinessServiceCategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessServiceCategoryRefRepository extends JpaRepository<BusinessServiceCategoryRef, Long> {
    Optional<BusinessServiceCategoryRef> findByBusSvcCatNmIgnoreCase(String busSvcCatNm);

}