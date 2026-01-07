package com.cpagoui_code.smart_clinic.data.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private Doctor doctor;
    @ManyToOne
    @NotNull
    private Patient patient;
    @Future
    private LocalDateTime appointmentTime;
    private int status; // 0 = Scheduled, 1 = Completed
    
    @Transient
    public LocalDateTime getEndTime() {
        return appointmentTime.plusHours(1);
    }

    @Transient
    public LocalDateTime getAppointmentTime(){
        return appointmentTime;
    }

    @Transient
    public LocalTime getAppointmentTimeOnly(){
        return appointmentTime.toLocalTime();
    }
}
