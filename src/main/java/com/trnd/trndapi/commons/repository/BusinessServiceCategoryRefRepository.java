package com.trnd.trndapi.commons.repository;

import com.trnd.trndapi.commons.entity.BusinessServiceCategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessServiceCategoryRefRepository extends JpaRepository<BusinessServiceCategoryRef, Long> {
}