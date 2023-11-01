package com.trnd.trndapi.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(
        AuditingEntityListener.class
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Auditable<T extends String> {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected T createdBy;
    @LastModifiedBy
    @Column(name = "modified_by")
    protected T modifiedBy;
    @CreatedDate
    @Column(name = "created_date" , updatable = false)
    protected LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;
}
