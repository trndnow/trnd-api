package com.trnd.trndapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subs_bill_id;
    @OneToOne
    private ProductMerchantSubscription prod_merch_subs_id;
    private LocalDate bill_date;
    private LocalDate bill_period_start_dt;
    private LocalDate bill_period_end_dt;
    private int bill_amount;
    private int bill_discount;
    private int bill_adjustment;
    private int bill_notes;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;
}
