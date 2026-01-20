package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Doctor;
import com.cpagoui_code.smart_clinic.data.repository.DoctorRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctors")
@Slf4j
public class DoctorController {
    private final DoctorRepository doctorRepository;

    /**
     * Constructs a DoctorController with the provided repository.
     *
     * @param doctorRepository repository used to manage doctors
     */
    public DoctorController(DoctorRepository doctorRepository) {
        super();
        this.doctorRepository = doctorRepository;
    }

    /**
     * Retrieve a doctor by its UUID.
     *
     * @param doctorId UUID of the doctor to fetch
     * @return the found Doctor
     * @throws NotFoundException if a doctor with the given id does not exist
     */
    @GetMapping("/{doctorId}")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Doctor getDoctor(@RequestBody UUID doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isPresent()) {
            log.info("Found doctor with ID: {}", doctorId);
            return doctor.get();
        } else {
            log.warn("No doctor found with ID: {}", doctorId);
            throw new NotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    /**
     * Create a new doctor. If a doctor with the same ID already exists, returns the existing entity.
     *
     * @param doctor Doctor object to create
     * @return the created or existing Doctor entity
     */
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(doctor.getDoctorId());
        if(existingDoctor.isPresent()) {
            log.info("Doctor with ID: {} already exists", existingDoctor.get().getDoctorId());
            return existingDoctor.get();
        }
        log.info("Creating new doctor: {}", doctor);    
        doctorRepository.save(doctor);
        log.info("Created new doctor with ID: {}", doctor.getDoctorId());
        return doctor;
    }

    /**
     * Delete a doctor by its UUID.
     *
     * @param doctorId UUID of the doctor to delete
     * @throws NotFoundException if a doctor with the given id does not exist
     */
    @DeleteMapping("/{doctorId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteDoctor(@RequestBody UUID doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isPresent()) {
            log.info("Deleting doctor with ID: {}", doctorId);
            doctorRepository.deleteById(doctorId);
        } else {
            log.warn("No doctor found with ID: {}", doctorId);
            throw new NotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    
}
