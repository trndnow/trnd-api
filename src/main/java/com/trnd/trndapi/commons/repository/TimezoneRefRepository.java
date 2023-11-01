package com.trnd.trndapi.commons.repository;

import com.trnd.trndapi.commons.entity.TimezoneRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimezoneRefRepository extends JpaRepository<TimezoneRef, Long> {
}
