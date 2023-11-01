package com.trnd.trndapi.security.entity;

import com.trnd.trndapi.security.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    @Enumerated(EnumType.STRING)
    private ERole name;
    private String description;
    private Integer createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    @Column(nullable = true)
    private Integer modifiedBy;
}
