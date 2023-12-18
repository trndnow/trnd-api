package com.trnd.trndapi.security.playload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trnd.trndapi.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private UUID uuid;
    private String username;
    private String email;
    private ERole role;
    private boolean isProfileCompleted = false;
    private int profileCompletionPercentage = 0;


}