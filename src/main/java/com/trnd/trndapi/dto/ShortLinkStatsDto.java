package com.trnd.trndapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortLinkStatsDto {

    private String shortCode;
    private int visitCount;
    private Long userId;
    private String originalUrl;
}
