package com.cpagoui_code.smart_clinic.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Doctor;

public interface  DoctorRepository extends JpaRepository<Doctor, Long>{
    Doctor findDoctorById(Long doctorId);
    Optional<Doctor> findByEmail(String email);
}
