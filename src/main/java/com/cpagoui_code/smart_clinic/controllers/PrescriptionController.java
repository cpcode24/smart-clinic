package com.cpagoui_code.smart_clinic.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cpagoui_code.smart_clinic.data.entity.Prescription;
import com.cpagoui_code.smart_clinic.data.repository.PrescriptionRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/prescriptions")
@Slf4j
public class PrescriptionController {
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionController(PrescriptionRepository prescriptionRepository) {
        super();
        this.prescriptionRepository = prescriptionRepository;
    }

    @GetMapping("/{id}")
    public Prescription getMethodName(@RequestParam UUID prescriptionId) throws NotFoundException {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) {
            log.info("Found prescription with ID: {}", prescriptionId);
            return prescription.get(); 
        } else {
            log.warn("No prescription found with ID: {}", prescriptionId);
            throw new NotFoundException("Prescription with ID " + prescriptionId + " not found"); 
        }  
    }

    @GetMapping("/{PatientId}")
    @ResponseStatus(HttpStatus.OK)

    public List<Prescription> getPatientPrescriptions(@RequestBody UUID patientId) throws NotFoundException {
        List<Prescription> prescriptions = prescriptionRepository.getPatientPrescriptions(patientId);
        if (!prescriptions.isEmpty()) {
            log.info("Found prescriptions for patient ID: {}", patientId);
            return prescriptions;
        } else {
            log.warn("No prescriptions found for patient ID: {}", patientId);
            throw new NotFoundException("No prescriptions found for patient ID " + patientId);
        }
    }
    
    
}
