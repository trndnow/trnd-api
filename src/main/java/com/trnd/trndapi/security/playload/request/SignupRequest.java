package com.trnd.trndapi.security.playload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;
    private Set<String> role;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "(^$|\\d{10})")
    private String mobile;
    private String merchantName;
}
