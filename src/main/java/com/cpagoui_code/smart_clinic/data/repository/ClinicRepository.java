package com.cpagoui_code.smart_clinic.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {
    
}
