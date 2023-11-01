package com.trnd.trndapi.coupon.entity;

import com.trnd.trndapi.campaign.entity.Campaign;
import com.trnd.trndapi.coupon.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coupon_id;
    @ManyToOne
    private Campaign camp_id;
    private String coupon_code;
    private CouponStatus coupon_status;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;
}
