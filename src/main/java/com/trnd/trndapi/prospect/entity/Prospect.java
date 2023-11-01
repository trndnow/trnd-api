package com.trnd.trndapi.prospect.entity;

import com.trnd.trndapi.commons.entity.AddressCityRef;
import com.trnd.trndapi.commons.entity.AddressCountryRef;
import com.trnd.trndapi.commons.entity.AddressStateRef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prospect")
public class Prospect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pros_id;
    private String pros_first_nm;
    private String pros_last_nm;
    private String pros_contact_email;
    private String pros_contact_phone;
    private String pros_addr_ln1;
    private String pros_addr_ln2;
    @ManyToOne
    private AddressCityRef addr_city_id;
    @ManyToOne
    private AddressStateRef addr_state_id;
    private String pros_addr_zip;
    @ManyToOne
    private AddressCountryRef addr_cntry_id;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;
}
