package com.cpagoui_code.smart_clinic.data.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "prescriptions")
public class Prescription {
    @Id
    private UUID prescriptionId;
    @NotNull
    private Long appointmentId;
    @NotNull
    @Size(min = 3, max = 100)
    private String medication;
    @NotNull
    @Size(min = 3, max = 20)
    private String dosage;
    @Size(max = 200)
    private String doctorNotes;

    @NotNull
    private UUID patientId;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }



}
