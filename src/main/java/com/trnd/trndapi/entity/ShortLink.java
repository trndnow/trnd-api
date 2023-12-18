package com.trnd.trndapi.entity;

import com.trnd.trndapi.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    private String originalUrl;

    @ManyToOne
    private User user;

    private int visitCount = 0; // This will track the number of times the link has been visited

}
