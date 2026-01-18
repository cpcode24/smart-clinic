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

import com.cpagoui_code.smart_clinic.data.entity.Clinic;
import com.cpagoui_code.smart_clinic.data.repository.ClinicRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clinics")
@Slf4j
public class ClinicController {
    
    private final ClinicRepository clinicRepository;

    public ClinicController(ClinicRepository clinicRepository) {
        super();
        this.clinicRepository = clinicRepository;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Clinic createProduct(@RequestBody Clinic clinic) {
        //TODO: process POST request
        log.info("Added clinic: {}", clinic);
        return clinicRepository.save(clinic);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Clinic getProduct(@PathVariable UUID id) {
        Optional<Clinic> product = clinicRepository.findById(id);
        if(product.isPresent()) {
            log.info("Found clinic with ID: {}", id);
            return product.get();
        } else {
            log.warn("No clinic found with ID: {}", id);
            throw new NotFoundException("Clinic with ID " + id + " not found");
        }
    }
}
