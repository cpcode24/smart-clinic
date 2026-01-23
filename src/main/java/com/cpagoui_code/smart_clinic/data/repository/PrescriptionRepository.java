package com.cpagoui_code.smart_clinic.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cpagoui_code.smart_clinic.data.entity.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, UUID>{

    void deleteById(UUID id);
    
}

