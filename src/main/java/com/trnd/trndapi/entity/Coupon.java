package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private long couponId;
    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Campaign campaign;
    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "coupon_status")
    private CouponStatus couponStatus;

}
