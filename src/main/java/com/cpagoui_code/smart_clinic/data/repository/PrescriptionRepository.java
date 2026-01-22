package com.cpagoui_code.smart_clinic.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID>{
 
    void deleteById(UUID id);
    List<Prescription> getPatientPrescriptions(UUID patientId);
    
}
