package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.ShortLinkStatsDto;
import com.trnd.trndapi.entity.ShortLink;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService{
    private final ShortLinkRepository shortLinkRepository;
    @Override
    public ShortLink createShortLinkForUser(String originalUrl, User user) {
        String uniqueCode = generateUniqueShortCode();
        ShortLink shortLink = new ShortLink();
        shortLink.setOriginalUrl(originalUrl);
        shortLink.setShortCode(uniqueCode);
        shortLink.setUser(user);
        return shortLinkRepository.save(shortLink);
    }

    @Override
    public void incrementVisitCount(String shortCode) {
        shortLinkRepository.findByShortCode(shortCode).ifPresent(shortLink -> {
            shortLink.setVisitCount(shortLink.getVisitCount() + 1);
            shortLinkRepository.save(shortLink);
        });
    }

    public Optional<ShortLink> getOriginalUrlByShortCode(String shortCode) {
        return shortLinkRepository.findByShortCode(shortCode);
    }

    @Override
    public List<ShortLinkStatsDto> getReferralStatsForUser(Long userId) {
        List<ShortLink> links = shortLinkRepository.findByUserId(userId);
        return links.stream()
                .map(link -> new ShortLinkStatsDto(link.getShortCode(), link.getVisitCount(), link.getUser().getId(), link.getOriginalUrl()))
                .collect(Collectors.toList());
    }

    private String generateUniqueShortCode() {
        // Simple unique code generation. You can use more advanced methods.
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
