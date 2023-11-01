package com.trnd.trndapi.product.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.trnd.trndapi.product.entity.Product}
 */
@Value
public class ProductDto implements Serializable {
    long prod_id;
    @NotNull
    @NotEmpty
    @NotBlank
    String prod_nm;
    String prodDescr;
    String prod_version;
    @PastOrPresent
    LocalDate prod_launch_dt;
    String prodIsActiveFlg;
    String prodBillUnit;
    String prodSubsLevelCost0;
    String prodSubsLevelCost1;
    String prodSubsLevelCost2;
    String prodSubsLevelCost3;
    String prodSubsLevelCost4;
    String prodSubsLevelCost5;
    String prodSubsTransactPct;
}