package com.cpagoui_code.smart_clinic.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    Optional<Appointment> findById(Long id);
    void deleteById(Long id);
}



