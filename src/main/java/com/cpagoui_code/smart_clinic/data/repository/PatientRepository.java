package com.cpagoui_code.smart_clinic.data.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Patient;

public interface  PatientRepository extends JpaRepository<Patient, UUID>{
    Patient findPatientById(UUID patientId);
    Optional<Patient> findByEmail(String email);
}
