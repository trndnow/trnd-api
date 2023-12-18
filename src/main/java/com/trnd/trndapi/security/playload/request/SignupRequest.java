package com.trnd.trndapi.security.playload.request;

import com.trnd.trndapi.annotation.ValidRole;
import com.trnd.trndapi.enums.ERole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;
    @ValidRole
    private ERole role;
    @NotNull
    @Size(min = 6, max = 40)
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "(^$|\\d{10})")
    private String mobile;
    private String merchantName;
    private String merchantCode;
    private String otp;

}
