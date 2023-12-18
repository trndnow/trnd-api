package com.trnd.trndapi.entity;

import com.trnd.trndapi.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
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
