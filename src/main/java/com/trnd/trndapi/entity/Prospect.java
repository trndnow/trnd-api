package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.entity.Address;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prospect")
public class Prospect extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pros_id")
    private long prosId;
    @Column(name = "pros_first_nm")
    private String prosFirstNm;
    @Column(name = "pros_last_nm")
    private String prosLastNm;
    @Column(name = "pros_contact_email")
    private String prosContactEmail;
    @Column(name = "pros_contact_phone")
    private String prosContactPhone;
    @Column(name = "pros_addr_ln1")
    private String prosAddrLn1;
    @Column(name = "pros_addr_ln2")
    private String prosAddrLn2;
    @ManyToOne
    @JoinColumn(name = "addr_id")
    private Address addrId;
}
