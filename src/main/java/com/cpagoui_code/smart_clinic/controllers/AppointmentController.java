package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Appointment;
import com.cpagoui_code.smart_clinic.data.repository.AppointmentRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cpagoui_code.smart_clinic.util.NotFoundException;



@RestController 
@RequestMapping("/appointments")
@Slf4j
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;

    /**
     * Constructs an AppointmentController with the provided repository.
     *
     * @param appointmentRepository repository used to manage appointments
     */
    public AppointmentController(AppointmentRepository appointmentRepository) {
        super();
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Create a new appointment. If an appointment with the same ID already exists,
     * returns the existing entity.
     *
     * @param appointment appointment to create
     * @return the created or existing Appointment entity
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment postMethodName(@RequestBody Appointment appointment) {
        //TODO: process POST request
        Optional<Appointment> existingAppointment = appointmentRepository.findById(appointment.getAppointmentId());
        if(existingAppointment.isPresent()) {
            log.info("Appointment with ID: " + existingAppointment.get().getAppointmentId() + " already exists");
            return existingAppointment.get();
        }
        Appointment entity = appointmentRepository.save(appointment);
        return entity;
    }

    /**
     * Retrieve an appointment by its ID.
     *
     * @param appointmentId UUID of the appointment to fetch
     * @return the found Appointment
     * @throws NotFoundException if appointment is not found
     */
    @GetMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public Appointment getAppointment(@RequestBody UUID appointmentId) throws NotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(appointment.isPresent()) {
            log.info("Found appointment with ID: {}", appointmentId);
            return appointment.get();
        } else {
            log.warn("No appointment found with ID: {}", appointmentId);
            throw new NotFoundException("Appointment with ID " + appointmentId + " not found");
        }
    }
    
    /**
     * Delete an appointment by its ID.
     *
     * @param appointmentId UUID of the appointment to delete
     * @throws NotFoundException if appointment is not found
     */
    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteAppointment(@RequestBody UUID appointmentId) throws NotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(appointment.isPresent()) {
            log.info("Deleting appointment with ID: {}", appointmentId);
            appointmentRepository.deleteById(appointmentId);
        } else {
            log.warn("No appointment found with ID: {}", appointmentId);
            throw new NotFoundException("Appointment with ID " + appointmentId + " not found");
        }
    }

    /**
     * Partially update an existing appointment.
     *
     * @param updatedAppointment Appointment object containing updated fields (must include appointmentId)
     * @return the updated Appointment
     * @throws NotFoundException if the appointment to update does not exist
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Appointment updateAppointment(@RequestBody Appointment updatedAppointment) throws NotFoundException {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(updatedAppointment.getAppointmentId());
        if(existingAppointment.isPresent()) {
            log.info("Updating appointment with ID: {}", updatedAppointment.getAppointmentId());
            Appointment currAppointment = existingAppointment.get();
            // Update fields
            currAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
            currAppointment.setAppointmentDoctor(updatedAppointment.getAppointmentDoctor());
            currAppointment.setAppointmentPatient(updatedAppointment.getAppointmentPatient());
            return appointmentRepository.save(currAppointment);
        } else {
            log.warn("No appointment found with ID: {}", updatedAppointment.getAppointmentId());
            throw new NotFoundException("Appointment with ID " + updatedAppointment.getAppointmentId() + " not found");
        }
    }
}
