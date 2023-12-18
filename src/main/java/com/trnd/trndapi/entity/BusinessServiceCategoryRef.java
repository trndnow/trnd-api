package com.trnd.trndapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business_service_category_ref")
public class BusinessServiceCategoryRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_svc_cat_id")
    private Long busSvcCatId;
    @Column(nullable = false, name = "bus_svc_cat_nm")
    private String busSvcCatNm;
    @Column(name = "descr")
    private String descr;
}