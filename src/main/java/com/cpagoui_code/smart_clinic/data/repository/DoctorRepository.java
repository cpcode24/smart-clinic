package com.cpagoui_code.smart_clinic.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Doctor;

public interface  DoctorRepository extends JpaRepository<Doctor, UUID>{
    Optional<Doctor> findDoctorById(UUID doctorId);
    Optional<Doctor> findByEmail(String email);
}
