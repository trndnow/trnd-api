package com.trnd.trndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trnd.trndapi.entity.Token;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.serializer.EmailMaskingSerializer;
import com.trnd.trndapi.serializer.LocalDateTimeSerializer;
import com.trnd.trndapi.serializer.MobileMaskingSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private UUID uniqueId;
    private String userCode;
    @JsonSerialize(using = EmailMaskingSerializer.class)
    private String email;
    @JsonIgnore
    private String password;
    @JsonSerialize(using = MobileMaskingSerializer.class)
    private String mobile;
    private boolean emailVerifiedFlag;
    @Enumerated(EnumType.STRING)
    private AccountStatus userStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registrationDateTime;
    private boolean softDeleted;
    @JsonIgnore
    private List<Token> tokens;

}
