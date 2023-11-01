package com.trnd.trndapi.campaign.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_category_ref")
public class CampaignCategoryRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long camp_cat_id;
    @NotNull
    private String camp_cat_nm;
}
