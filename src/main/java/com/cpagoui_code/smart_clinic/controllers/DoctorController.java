package com.cpagoui_code.smart_clinic.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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

import com.cpagoui_code.smart_clinic.data.entity.Doctor;
import com.cpagoui_code.smart_clinic.data.repository.DoctorRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * Constructs a DoctorController with the provided repository.
     *
     * @param doctorRepository repository used to manage doctors
     */
    public DoctorController(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        super();
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieve a doctor by its Long.
     *
     * @param doctorId Long of the doctor to fetch
     * @return the found Doctor
     * @throws NotFoundException if a doctor with the given id does not exist
     */
    @GetMapping("/{doctorId}")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Doctor getDoctor(@PathVariable Long doctorId) {
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
     * Login a doctor.
     *
     * @param email email of the doctor to login string
     * @param password password of the doctor to login string
     * @return dashboard view if successful, login view with error otherwise
     */
    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent() && password != null && password.equals(doctor.get().getPassword())) {
            log.info("Doctor {} logged in", email);
            return "doctor/doctorDashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "doctor/doctorLogin";
    }

    /**
     * Show the login page.
     * @return login view
     */
    @GetMapping("/login")
    public String showLogin() {
        return "doctor/doctorLogin";
    }

    /**
     * Show the registration page.
     * @return registration view
     */
    @GetMapping("/register")
    public String showRegister() {
        return "doctor/doctorRegistration";
    }

    /**
     * Add a new doctor. If a doctor with the same ID already exists, returns the existing entity.
     *
     * @param doctor Doctor object to create
     * @return the created or existing Doctor entity
     */
    @PostMapping("register-doctor")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Doctor addDoctor(@RequestBody Doctor doctor, Model model) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(doctor.getDoctorId());
        if(existingDoctor.isPresent()) {
            log.info("Doctor with ID: {} already exists", existingDoctor.get().getDoctorId());
            return existingDoctor.get();
        }
        log.info("Creating new doctor: {}", doctor);    
        doctorRepository.save(doctor);
        log.info("Created new doctor with ID: {}", doctor.getDoctorId());
        model.addAttribute("message", "Doctor created successfully with ID: " + doctor.getDoctorId());
        return doctor;
    }

    /**
     * Delete a doctor by its Long.
     *
     * @param doctorId Long of the doctor to delete
     * @throws NotFoundException if a doctor with the given id does not exist
     */
    @DeleteMapping("/{doctorId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if(doctor != null) {
            log.info("Deleting doctor with ID: {}", doctorId);
            doctorRepository.deleteById(doctorId);
        } else {
            log.warn("No doctor found with ID: {}", doctorId);
            throw new NotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    /**
     * Update a doctor's password.
     *
     * @param passwords map containing "oldPassword" and "newPassword"
     * @param id doctor id
     * @return true if password updated successfully, false if old password does not match
     */
    @PatchMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean updatePassword(@RequestBody Map<String, String> passwords, @PathVariable Long id) {
        Doctor doctor = doctorRepository.findDoctorById(id);
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        if (!passwordEncoder.matches(oldPassword, doctor.getPassword())) {
             return false;
        }
            doctor.setPassword(passwordEncoder.encode(newPassword));
            //updateEntity(doctor);
            return true;
    }

     /**
     * Check if the provided password matches the stored password for the patient.
     *
     * @param password password to check
     * @param id doctor id
     * @return true if passwords match, false otherwise
     */
    @PostMapping("/{id}/check-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPassword(@RequestBody String password, @PathVariable Long id) {
        Doctor doctor = doctorRepository.findDoctorById(id);
        return passwordEncoder.matches(password, doctor.getPassword());
    }

    /**
     * Redirect root /doctor to the login page.
     *
     * @return redirect to login view
     */
    @GetMapping({"", "/"})
    public String root() {
        // Redirect bare /patients to the login page
        return "redirect:/doctor/login";
    }
}
