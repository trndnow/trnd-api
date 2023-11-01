package com.trnd.trndapi.security.playload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String mobile;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
