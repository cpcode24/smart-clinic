package com.cpagoui_code.smart_clinic.data.entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @NotNull
    private Long patient_id;
    @NotNull
    private double amount;
    @NotNull
    private String paymentMethod;
}
