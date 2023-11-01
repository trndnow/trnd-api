package com.trnd.trndapi.security.entity;

import com.trnd.trndapi.security.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "uniqueId"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uniqueId;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    @Column(nullable = false, unique = true)
    private String mobile;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn( name = "role_id")
    )
    private Set<Role> roles;
    @Column(name = "is_softDeleted")
    private Boolean softDeleted = Boolean.FALSE;
    private AccountStatus accountStatus;
    public User(UUID uniqueId, String email, String password) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.password = password;
    }
}
