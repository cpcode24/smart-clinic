package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Clinic;
import com.cpagoui_code.smart_clinic.data.repository.ClinicRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clinics")
@Slf4j
public class ClinicController {
    
    private final ClinicRepository clinicRepository;

    /**
     * Constructs a ClinicController with the provided repository.
     *
     * @param clinicRepository repository used to manage clinics
     */
    public ClinicController(ClinicRepository clinicRepository) {
        super();
        this.clinicRepository = clinicRepository;
    }
    
    /**
     * Create a new clinic and persist it.
     *
     * @param clinic Clinic object to create
     * @return the saved Clinic entity
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Clinic createClinic(@RequestBody Clinic clinic) {
        //TODO: process POST request
        log.info("Added clinic: {}", clinic);
        return clinicRepository.save(clinic);
    }

    /**
     * Retrieve a clinic by its Long.
     *
     * @param id Long of the clinic to fetch
     * @return the found Clinic
     * @throws NotFoundException if a clinic with the given id does not exist
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Clinic getClinic(@PathVariable Long clinicId) {
        Optional<Clinic> clinic = clinicRepository.findById(clinicId);
        if(clinic.isPresent()) {
            log.info("Found clinic with ID: {}", clinicId);
            return clinic.get();
        } else {
            log.warn("No clinic found with ID: {}", clinicId);
            throw new NotFoundException("Clinic with ID " + clinicId + " not found");
        }
    }
}
