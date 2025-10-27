package com.payment.paymentsvc.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.Long;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Version
    private Long version;
}
