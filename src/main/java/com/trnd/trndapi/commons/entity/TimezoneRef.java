package com.trnd.trndapi.commons.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "timezone_ref")
public class TimezoneRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long timezone_id;
    private String timezone_nm;
    private String abbreviation;
    private String utc_offset;
    private String country;
    private String region;
}
