package com.cpagoui_code.smart_clinic.data.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.cglib.core.Local;

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
    private UUID appointmentId;
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
    public void setAppointmentTime(LocalDateTime appointmentTime){
        this.appointmentTime = appointmentTime;
    }
    @Transient
    public LocalTime getAppointmentTimeOnly(){
        return appointmentTime.toLocalTime();
    }
    public Doctor getAppointmentDoctor() {
        return doctor;
    }
    public void setAppointmentDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Patient getAppointmentPatient() {
        return patient;
    }
    public void setAppointmentPatient(Patient patient) {
        this.patient = patient;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }
}
