package com.trnd.trndapi.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trnd.trndapi.security.entity.Token;
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
public class UserDto {
    private Long id;
    private UUID uniqueId;
    private String email;
    private String password;
    private String mobile;
    @JsonIgnore
    private List<Token> tokens;
}
