package com.cpagoui_code.smart_clinic.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    /**
     * Constructs a PatientController with the provided repository.
     *
     * @param patientRepository repository used to manage patient data
     */
    public PatientController(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        super();
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Add a new patient to the system. If a patient with the same id already exists,
     * the existing entity will be returned.
     *
     * @param newPatient patient data to add
     * @return the saved or existing Patient
     */
    @PostMapping("/register-patient")
    @ResponseStatus(HttpStatus.CREATED )
    public Patient addPatient(@RequestBody Patient newPatient) {
        Optional<Patient> patient = Optional.ofNullable(patientRepository.findPatientById(newPatient.getId()));
        if(patient.isPresent()) {
            log.info("Patient with ID: {} already exists", patient.get().getId());
            return patient.get();
        } else {
            newPatient.setPassword(passwordEncoder.encode(newPatient.getPassword()));
            log.info("Adding patient: {}", newPatient);
            return patientRepository.save(newPatient);
        }
    }
    
    /**
     * Retrieve a patient by their id.
     *
     * @param id patient id
     * @return the found Patient
     * @throws NotFoundException if no patient with the id exists
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatient(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()) {
            log.info("Found patient with ID: {}", id);
            return patient.get();
        } else {
            log.warn("No patient found with ID: {}", id);
            throw new NotFoundException("Patient with ID " + id + " not found");
        }
    }

    /**
     * Render the patient login view.
     *
     * @return view name for the patient login page
     */
    @GetMapping("/login")
    public String showLogin() {
        return "patient/patientLogin";
    }

    /**
     * Render the patient registration view.
     *
     * @return view name for the patient registration page
     */
    @GetMapping("/register")
    public String showRegister() {
        return "patient/patientRegistration";
    }

    /**
     * Authenticate a patient using email and password form parameters.
     * On success returns the patient dashboard view, otherwise the login view
     * with an error message added to the model.
     *
     * @param email submitted email
     * @param password submitted password
     * @param model Spring MVC model used to pass attributes to the view
     * @return view name to render
     */
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
    public void deletePatient(@PathVariable Long id) {
        Optional<Patient> patient = patientRepository.findById(id); 
        if(patient.isPresent()) {
            log.info("Deleting patient with ID: {}", id);
            patientRepository.delete(patient.get());
        } else {
            log.warn("No patient found with ID: {}", id);
            throw new NotFoundException("Patient with ID " + id + " not found");
        }
    }

    /*
     * Update a patient by its id.
     *
     * @param id of the patient to update
     * @param updatedPatient Patient object with updated information
     * @return the updated Patient
     * @throws NotFoundException if a patient with the given id does not exist
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
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

    /**
     * Redirect root /patient to the login page.
     *
     * @return redirect string to patient login
     */
    @GetMapping({"", "/"})
    public String root() {
        // Redirect bare /patients to the login page
        return "redirect:/patient/login";
    }

    /**
     * Check if the provided password matches the stored password for the patient.
     *
     * @param password password to check
     * @param id patient id
     * @return true if passwords match, false otherwise
     */
    @PostMapping("/{id}/check-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPassword(@RequestBody Object password, @PathVariable Long id) {
        Patient patient = patientRepository.findPatientById(id);
        String stringPassword = password.toString();
        return passwordEncoder.matches(stringPassword, patient.getPassword());
    }

    /**
     * Update a patient's password.
     *
     * @param passwords map containing "oldPassword" and "newPassword"
     * @param id patient id
     * @return true if password updated successfully, false if old password does not match
     */
    @PatchMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean updatePassword(@RequestBody Map<String, String> passwords, @PathVariable Long id) {
        Patient patient = patientRepository.findPatientById(id);
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        if (!passwordEncoder.matches(oldPassword, patient.getPassword())) {
             return false;
        }
            patient.setPassword(passwordEncoder.encode(newPassword));
            //updateEntity(patient);
            return true;

    }

    /**
     * Password encoder bean.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
