package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Patient;
import com.cpagoui_code.smart_clinic.data.repository.PatientRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("patients")
@Slf4j
public class PatientController {
    private final PatientRepository patientRepository;
    
    public PatientController(PatientRepository patientRepository) {
        super();
        this.patientRepository = patientRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED )

    public Patient addPatient(@RequestBody Patient patient) {
        //Optional<Patient> patient = patientRepository.findById(patient.id);
        //if(patient.isPresent()) {
            //log.info("Patient with ID: {} already exists", id);
            //return patient.get();
        //} else {
            log.info("Added patient: {}", patient);
            return patientRepository.save(patient);
        //}
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatient(@PathVariable UUID id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()) {
            log.info("Found patient with ID: {}", id);
            return patient.get();
        } else {
            log.warn("No patient found with ID: {}", id);
            throw new NotFoundException("Patient with ID " + id + " not found");
        }
    }
}

