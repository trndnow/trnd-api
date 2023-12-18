package com.trnd.trndapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prod_id;
    @NotNull
    @Column(unique = true,nullable = false)
    private String prod_nm;
    private String prodDescr;
    @Column(nullable = false)
    private String prod_version;
    @Column(nullable = false)
    private LocalDate prod_launch_dt;
    @Column(nullable = false)
    private String prodIsActiveFlg;
    @Column(nullable = false)
    private String prodBillUnit;
    private String prodSubsLevelCost0;
    private String prodSubsLevelCost1;
    private String prodSubsLevelCost2;
    private String prodSubsLevelCost3;
    private String prodSubsLevelCost4;
    private String prodSubsLevelCost5;
    private String prodSubsTransactPct;
}
