package com.cpagoui_code.smart_clinic.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cpagoui_code.smart_clinic.data.entity.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, Long> {

    void deleteById(Long id);
    
}

