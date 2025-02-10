package com.example.dentistms.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class Audit {

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
