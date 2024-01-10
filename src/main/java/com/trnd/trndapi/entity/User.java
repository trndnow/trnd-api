package com.trnd.trndapi.entity;

import com.trnd.trndapi.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "unique_id"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false,name = "unique_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uniqueId;
    @Column(name = "user_code",unique = true,nullable = false, updatable = false)
    private String userCode;
    @Email
    private String email;
    private boolean emailVerifiedFlag = false;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    @Column(nullable = false, unique = true)
    private String mobile;
    @ManyToOne
    private Role role;
    @Column(name = "is_soft_deleted")
    private boolean softDeleted = false;
    @Enumerated(EnumType.STRING)
    private AccountStatus userStatus;
    private LocalDateTime registrationDateTime;
    private LocalDateTime lastLoginDateTime;
    private LocalDateTime accountDeletedDateTime;
    public User(UUID uniqueId, String email, String password) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.password = password;
    }

}
