
/*************************************************************************
 *   Clinic.java                               
 *   Author : Constant Pagoui                         
 *   Date   : 2026-01-05                        
 *                                              
 *   Summary:                                   
 *   Represents a medical clinic with contact and location details and the list of       
 *   associated doctors.                        
 * **************************************************************************/


package com.cpagoui_code.smart_clinic.data.entity;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID clinicId;
    @NotNull
    private String clinicName;
    @NotNull
    @Size(min = 10, max = 100)
    private String address;

    @NotNull
    private String phoneNumber;

    private int zipCode;

    @Size(min = 3, max = 100)
    @NotNull
    private String city;

    @NotNull
    private String state;
    
}
