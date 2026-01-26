package com.cpagoui_code.smart_clinic.data.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Patient;

public interface  PatientRepository extends JpaRepository<Patient, Long>{
    Patient findPatientById(Long patientId);
    Optional<Patient> findByEmail(String email);
}
