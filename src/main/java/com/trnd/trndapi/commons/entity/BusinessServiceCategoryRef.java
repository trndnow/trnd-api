package com.trnd.trndapi.commons.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business_service_category_ref")
public class BusinessServiceCategoryRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long bus_svc_cat_id;
    @Column(nullable = false)
    private String bus_svc_cat_nm;

}