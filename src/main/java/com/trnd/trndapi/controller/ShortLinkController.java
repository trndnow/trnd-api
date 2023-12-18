package com.trnd.trndapi.controller;

import com.trnd.trndapi.entity.ShortLink;
import com.trnd.trndapi.service.ShortLinkService;
import com.trnd.trndapi.dto.ShortLinkStatsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/r")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletRequest request) {
        Optional<ShortLink> shortLinkOpt = shortLinkService.getOriginalUrlByShortCode(shortCode);

        if (shortLinkOpt.isPresent()) {
            ShortLink shortLink = shortLinkOpt.get();
            shortLinkService.incrementVisitCount(shortCode); // Increment the visit count

            // Redirect the user to the originalUrl (which is your application's landing page)
            String redirectUrl = shortLink.getOriginalUrl();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(redirectUrl);

            // If you need to append query parameters (e.g., for tracking), you can do so like this
            uriBuilder.queryParam("ref", shortCode);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriBuilder.build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/stats/{userId}")
    public ResponseEntity<List<ShortLinkStatsDto>> getReferralStats(@PathVariable Long userId) {
        List<ShortLinkStatsDto> stats = shortLinkService.getReferralStatsForUser(userId);
        if (stats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
