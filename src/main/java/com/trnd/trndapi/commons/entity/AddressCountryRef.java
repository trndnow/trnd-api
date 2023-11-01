package com.trnd.trndapi.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_country_ref")
public class AddressCountryRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addr_cntry_id;
    private String addr_cntry_nm;

}
