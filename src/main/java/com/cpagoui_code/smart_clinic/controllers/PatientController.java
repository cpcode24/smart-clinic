package com.cpagoui_code.smart_clinic.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cpagoui_code.smart_clinic.data.entity.Prescription;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/patients")
@Slf4j
public class PatientController {
    private final PatientRepository patientRepository;
    
    public PatientController(PatientRepository patientRepository) {
        super();
        this.patientRepository = patientRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED )
    public Patient addPatient(@RequestBody Patient newPatient) {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findPatientById(newPatient.getId()));
        if(patient.isPresent()) {
            log.info("Patient with ID: {} already exists", patient.get().getId());
            return patient.get();
        } else {
            log.info("Adding patient: {}", newPatient);
            return patientRepository.save(newPatient);
        }
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable UUID id) {
        Optional<Patient> patient = patientRepository.findById(id); 
        if(patient.isPresent()) {
            log.info("Deleting patient with ID: {}", id);
            patientRepository.delete(patient.get());
        } else {
            log.warn("No patient found with ID: {}", id);
            throw new NotFoundException("Patient with ID " + id + " not found");
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient updatePatient(@PathVariable UUID id, @RequestBody Patient updatedPatient) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()) {
            log.info("Updating patient with ID: {}", id);
            Patient existingPatient = patient.get();
            // Update fields
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
            existingPatient.setAddress(updatedPatient.getAddress());
            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setMedicalHistory(updatedPatient.getMedicalHistory());
            return patientRepository.save(existingPatient);
        } else {
            log.warn("No patient found with ID: {}", id);
            throw new NotFoundException("Patient with ID " + id + " not found");
        }
    }

   
}
