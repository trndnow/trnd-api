package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  //  List<Address> findByStateCodeLikeIgnoreCaseOrCityLikeIgnoreCaseOrTimezoneLikeIgnoreCaseOrZipcodeGreaterThanEqual(String stateCode, String city, String timezone, int zipcode);
    List<Address> findByStateCodeContainingIgnoreCaseOrCityContainingIgnoreCaseOrZipcodeGreaterThanEqual(String term, String term1, int zipcode);

    @Query("""
            select a from Address a
            where a.id = ?1 and a.stateCode = ?2 and a.city = ?3 and a.zipcode = ?4 and a.timezone = ?5""")
    Address findByIdAndStateCodeAndCityAndZipcodeAndTimezone(long id, String stateCode, String city, int zipcode, String timezone);
}