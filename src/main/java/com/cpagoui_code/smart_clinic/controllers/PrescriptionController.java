package com.cpagoui_code.smart_clinic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Patient;
import com.cpagoui_code.smart_clinic.data.entity.Prescription;
import com.cpagoui_code.smart_clinic.data.repository.PatientRepository;
import com.cpagoui_code.smart_clinic.data.repository.PrescriptionRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/prescriptions")
@Slf4j
public class PrescriptionController {
    private PrescriptionRepository prescriptionRepository;
    private PatientRepository patientRepository;

    public PrescriptionController(PrescriptionRepository prescriptionRepository, PatientRepository patientRepository) {
        super();
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;     
    }

    @GetMapping("/{id}")
    public Prescription getMethodName(@RequestParam Long prescriptionId) throws NotFoundException {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) {
            log.info("Found prescription with ID: {}", prescriptionId);
            return prescription.get(); 
        } else {
            log.warn("No prescription found with ID: {}", prescriptionId);
            throw new NotFoundException("Prescription with ID " + prescriptionId + " not found"); 
        }  
    }

    @GetMapping("/{PatientId}/prescriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<Prescription> getPatientPrescriptions(@PathVariable Long patientId) throws NotFoundException {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (!patient.isPresent()) {
            log.warn("No patient found with ID: {}", patientId);
            throw new NotFoundException("Patient with ID " + patientId + " not found");
        }

        List<Long> prescriptionIds = patient.get().getPrescriptions();
        if (prescriptionIds.isEmpty()) {
            log.warn("No prescriptions found for patient ID: {}", patientId);
            return List.of();
        }

        List<Prescription> prescriptions = prescriptionRepository.findAllById(prescriptionIds);
        if (!prescriptions.isEmpty()) {
            log.info("Found prescriptions for patient ID: {}", patientId);
            return prescriptions;
        } else {
            log.warn("No prescriptions found for patient ID: {}", patientId);
            throw new NotFoundException("No prescriptions found for patient ID " + patientId);
        }
    }
    
    
}
