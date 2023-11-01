package com.trnd.trndapi.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_city_ref")
public class AddressCityRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addr_city_id;
    @ManyToOne
    private AddressStateRef addr_state_id;
    private String addr_city_nm;
    @Length(max = 5 ,min = 5)
    private String zipcode;
}
