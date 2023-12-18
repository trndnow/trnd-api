package com.trnd.trndapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_category_ref")
public class CampaignCategoryRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_cat_id")
    private long campCatId;
    @NotNull
    @Column(name = "camp_cat_nm")
    private String campCatNm;
    @Column(name = "descr")
    private String descr;
}
