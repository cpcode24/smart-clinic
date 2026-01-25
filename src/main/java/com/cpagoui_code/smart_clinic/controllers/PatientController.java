package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cpagoui_code.smart_clinic.data.entity.Patient;
import com.cpagoui_code.smart_clinic.data.repository.PatientRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    private final PatientRepository patientRepository;
    
    public PatientController(PatientRepository patientRepository) {
        super();
        this.patientRepository = patientRepository;
    }

    @PostMapping("/register-patient")
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

    @GetMapping("/login")
    public String showLogin() {
        return "patient/patientLogin";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "patient/patientRegistration";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isPresent() && password != null && password.equals(patient.get().getPassword())) {
            log.info("Patient {} logged in", email);
            return "patient/patientDashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "patient/patientLogin";
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

    @GetMapping({"", "/"})
    public String root() {
        // Redirect bare /patients to the login page
        return "redirect:/patient/login";
    }
}
