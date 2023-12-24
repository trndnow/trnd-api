package com.trnd.trndapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatistics {

    private long totalMerchantCount;
    private long totalAffiliateCount;
    private long totalCampaignCount;

}
