package com.trnd.trndapi.subscription.entity;

import com.trnd.trndapi.merchant.entity.Merchant;
import com.trnd.trndapi.product.entity.Product;
import com.trnd.trndapi.subscription.SubscriptionStatus;
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
@Entity
@Table(name = "product_merchant_subscription")
public class ProductMerchantSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prod_merch_subs_id;
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product prod_id;
    @ManyToOne
    @JoinColumn(name = "merch_id")
    private Merchant merch_id;
    private SubscriptionStatus subs_status;
    private LocalDate subs_start_date;
    private LocalDate subs_end_date;
    private String bill_frequency;
    private LocalDate bill_start_dt;
    private int bill_amount;
    private int bill_discount;
    private String bill_currency_cd;
    private LocalDate next_bill_dt;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;

}
