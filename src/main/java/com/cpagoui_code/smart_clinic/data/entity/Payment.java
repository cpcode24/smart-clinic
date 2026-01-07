package com.cpagoui_code.smart_clinic.data.entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @NotNull
    private Patient patient;
    @NotNull
    private double amount;
    @NotNull
    private String paymentMethod;
}
