package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  //  List<Address> findByStateCodeLikeIgnoreCaseOrCityLikeIgnoreCaseOrTimezoneLikeIgnoreCaseOrZipcodeGreaterThanEqual(String stateCode, String city, String timezone, int zipcode);
    List<Address> findByStateCodeContainingIgnoreCaseOrCityContainingIgnoreCaseOrZipcodeGreaterThanEqual(String term, String term1, int zipcode);
}