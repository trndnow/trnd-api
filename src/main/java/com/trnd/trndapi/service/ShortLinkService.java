package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.ShortLinkStatsDto;
import com.trnd.trndapi.entity.ShortLink;
import com.trnd.trndapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface ShortLinkService {

    ShortLink createShortLinkForUser(String originalUrl, User user);
    void incrementVisitCount(String shortCode);

    Optional<ShortLink> getOriginalUrlByShortCode(String shortCode);
    List<ShortLinkStatsDto> getReferralStatsForUser(Long userId);


}
