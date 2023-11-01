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
@Table(name = "address_state_ref")
public class AddressStateRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addr_state_id;
    @ManyToOne
    private AddressCountryRef addr_cntry_id;
    private String addr_state_nm;
}
