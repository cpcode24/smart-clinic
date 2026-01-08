package com.cpagoui_code.smart_clinic.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cpagoui_code.smart_clinic.data.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{
    Optional<Appointment> findById(UUID id);
    void deleteById(UUID id);
}



